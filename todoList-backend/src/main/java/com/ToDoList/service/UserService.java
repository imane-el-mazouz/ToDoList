package com.ToDoList.service;

import com.gestion.dto.AgentDTO;
import com.gestion.dto.UserDTO;
import com.gestion.exception.AgentNotFoundException;
import com.gestion.mapper.UserMapper;
import com.gestion.model.User;
import com.gestion.model.UserGrade;
import com.gestion.repository.DivisionRepository;
import com.gestion.repository.UserGradeRepository;
import com.gestion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final DivisionRepository divisionRepository;
    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserGradeRepository userGradeRepository;

    @Autowired
    public UserService(UserRepository userRepository , DivisionRepository divisionRepository) {
        this.userRepository = userRepository;
        this.divisionRepository=divisionRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public User createAgent(AgentDTO agentDTO) {
        // Création d'une nouvelle entité User à partir du DTO
        User user = new User();
        user.setNumero(agentDTO.getNumero());
        user.setMatricule(agentDTO.getMatricule());
        user.setNom(agentDTO.getNom());
        user.setPrenom(agentDTO.getPrenom());
        user.setSexe(agentDTO.getSexe());
        user.setEmail(agentDTO.getEmail());
        user.setGrade(agentDTO.getGrade()); // Utilisation directe du modèle UserGrade
        user.setFonction(agentDTO.getFonction());
        user.setClasseAgent(agentDTO.getClasse());
        user.setCoursier(agentDTO.isCoursier());
        user.setRole(agentDTO.getRole());

        // Ajout d'un mot de passe par défaut "0000"
        String defaultPassword = "0000";
        String hashedPassword = passwordEncoder.encode(defaultPassword);
        user.setPassword(hashedPassword);

        // Sauvegarde de l'entité User
        return userRepository.save(user);
    }


    public User updateAgent(Long userId, AgentDTO agentDTO) throws Exception {
        // Recherche de l'utilisateur existant
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé avec l'ID : " + userId));

        // Mise à jour des champs de l'utilisateur
        existingUser.setNumero(agentDTO.getNumero());
        existingUser.setMatricule(agentDTO.getMatricule());
        existingUser.setNom(agentDTO.getNom());
        existingUser.setPrenom(agentDTO.getPrenom());
        existingUser.setSexe(agentDTO.getSexe());
        existingUser.setEmail(agentDTO.getEmail());
        existingUser.setFonction(agentDTO.getFonction());
        existingUser.setClasseAgent(agentDTO.getClasse());
        existingUser.setCoursier(agentDTO.isCoursier());

        // Gestion du grade (UserGrade)
        if (agentDTO.getGrade() != null) {
            UserGrade grade = userGradeRepository.findById(agentDTO.getGrade().getId())
                    .orElseThrow(() -> new Exception("Grade non trouvé avec l'ID : " + agentDTO.getGrade().getId()));
            existingUser.setGrade(grade);
        }

        // Sauvegarde de l'utilisateur mis à jour
        return userRepository.save(existingUser);
    }


//    public User createUser(User user) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(user.getPassword());
//
//        // Set the hashed password to the user
//        user.setPassword(hashedPassword);
//
//        // Save the user to the repository
//        return userRepository.save(user);
//    }
public User createUser(User user) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Vérifier si le mot de passe est vide ou null
    String passwordToHash = (user.getPassword() == null || user.getPassword().isEmpty()) ? "0000" : user.getPassword();

    // Hacher le mot de passe
    String hashedPassword = passwordEncoder.encode(passwordToHash);

    // Définir le mot de passe haché pour l'utilisateur
    user.setPassword(hashedPassword);

    // Sauvegarder l'utilisateur dans le repository
    return userRepository.save(user);
}


    public void deleteUser(Long id) throws AgentNotFoundException {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AgentNotFoundException("Utilisateur non trouvé avec l'ID : " + id));
        userRepository.delete(existingUser);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) throws AgentNotFoundException {
        // Recherche de l'utilisateur existant par ID
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AgentNotFoundException("Utilisateur non trouvé avec l'ID : " + id));

        // Mise à jour des champs de l'utilisateur avec les données du DTO
        existingUser.setNom(userDTO.getNom());
        existingUser.setPrenom(userDTO.getPrenom());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setFonction(userDTO.getFonction());
        existingUser.setClasseAgent(userDTO.getClasseAgent());
        existingUser.setCoursier(userDTO.isCoursier());

        // Sauvegarde de l'utilisateur mis à jour
        User updatedUser = userRepository.save(existingUser);

        // Retourne l'utilisateur mis à jour en DTO
        return userMapper.toUserDTO(updatedUser);
    }


    public User assignGradeToUser(Long userId, Long gradeId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Utilisateur non trouvé avec l'ID " + userId));

        UserGrade grade = userGradeRepository.findById(gradeId)
                .orElseThrow(() -> new Exception("Grade non trouvé avec l'ID " + gradeId));

        user.setGrade(grade);
        return userRepository.save(user);
    }

    public AgentDTO getAgentById(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Agent non trouvé avec l'ID : " + userId));
        return convertToDTO(user); // Convertir l'entité User en DTO
    }

    public AgentDTO getAgentByMatricule(String matricule) throws Exception {
        User user = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new Exception("Agent non trouvé avec le matricule : " + matricule));
        return convertToDTO(user); // Convertir l'entité User en DTO
    }


    public List<AgentDTO> createAgentList() {
        // Récupérer tous les utilisateurs (agents)
        List<User> users = userRepository.findAll();

        // Convertir la liste d'utilisateurs en une liste de DTOs
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private AgentDTO convertToDTO(User user) {
        return new AgentDTO(
                user.getId(),
                user.getNumero(),
                user.getMatricule(),
                user.getNom(),
                user.getPrenom(),
                user.getSexe(),
                user.getEmail(),
                user.getGrade(),
                user.getFonction(),
                user.getClasseAgent(),
                user.isCoursier(),
                user.getRole()
        );
    }

    public void deleteAgent(Long userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("Agent non trouvé avec l'ID : " + userId));
        userRepository.delete(user);  // Supprimer l'agent
    }

    public List<AgentDTO> getAgentsByDivisionId(Long divisionId) {
        // Fetch users by division ID
        List<User> users = userRepository.findByDivisionId(divisionId);

        // Convert users to DTOs
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
