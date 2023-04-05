package com.fatec.starvingless.services;

import com.fatec.starvingless.dto.UserDTO;
import com.fatec.starvingless.entities.User;
import com.fatec.starvingless.repositories.UserRepository;
import com.fatec.starvingless.services.exceptions.ObjectNotFoundException;
import com.fatec.starvingless.services.exceptions.UserAlreadyExistsException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserRepository repository;

    @Autowired
    BCryptPasswordEncoder encoder;


//@Autowired
//    private final Firestore firestore;
//@Autowired
//    public UserService(Firestore firestore) {
//        this.firestore = firestore;
//    }
//
//
//    // IMPLEMENTAÇÃO FIREBASE
//
//    public UserFire findById(String id) throws ExecutionException, InterruptedException {
//        Firestore dbfirestore = FirestoreClient.getFirestore();DocumentReference documentReference = dbfirestore
//                .collection("user").document(id);
//       ApiFuture<DocumentSnapshot> future = documentReference.get();
//       DocumentSnapshot document = future.get();
//       UserFire user;
//       if(document.exists()) {
//           user = document.toObject(UserFire.class);
//           return user;
//       } else {
//           throw new ObjectNotFoundException("Id Not Found");
//       }
//
//    }
//
//    public UserFire create(UserFireDTO userDTO) throws ExecutionException, InterruptedException {
//        String email = userDTO.getEmail();
//        String cpf = userDTO.getCpf();
//
//        Query query = firestore.collection("users")
//                .whereEqualTo("email", email)
//                .limit(1);
//        QuerySnapshot querySnapshot = query.get().get();
//        if (!querySnapshot.isEmpty()) {
//            throw new IllegalArgumentException("Já existe um usuário com este e-mail.");
//        }
//
//        query = firestore.collection("users")
//                .whereEqualTo("cpf", cpf)
//                .limit(1);
//        querySnapshot = query.get().get();
//        if (!querySnapshot.isEmpty()) {
//            throw new IllegalArgumentException("Já existe um usuário com este CPF.");
//        }
//
//        UserFire user = new UserFire(userDTO.getId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getCpf(),
//                userDTO.getAddress(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getPhone(), userDTO.getSignUpDate());
//        Firestore dbfirestore = FirestoreClient.getFirestore();
//        DocumentReference documentReference = dbfirestore
//                .collection("user").document();
//        String documentId = documentReference.getId();
//        user.setId(documentId);
//
//        ApiFuture<WriteResult> writeResultApiFuture = documentReference.set(user);
//        WriteResult writeResult = writeResultApiFuture.get();
//
//        return user;
//    }



//    public String update(UserFire user) throws ExecutionException, InterruptedException {
//        Firestore dbfirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collctionApiFuture = dbfirestore.collection("user").document(user.getId())
//                .set(user);
//
//        return collctionApiFuture.get().getUpdateTime().toString();
//    }
//
//    public String delete(String id) throws ExecutionException, InterruptedException {
//        Firestore dbfirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> writeResult = dbfirestore.collection("user").document(id).delete();
//
//        return "Sucess delete!" + id;
//    }

    // ----------------------------------------------------------------

    // IMPLEMENTAÇÃO PADRÃO


    public User findById(Long id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(()-> new ObjectNotFoundException("ID not found!"));
    }


    public Page<User> findAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public User create(UserDTO userDTO){
        userDTO.setId(null);
        repository.findByCpf(userDTO.getCpf()).ifPresent(u -> { throw new
                UserAlreadyExistsException("CPF already exists.");});
        if (findByEmail(userDTO.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already exists.");
        }
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));
        return repository.save(mapper.map(userDTO, User.class));
    }

    public User findByEmail(String email) {
        return repository.findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }


    public User update(UserDTO userDTO) {
        User user = repository.findById(userDTO.getId()).orElseThrow(() -> new
                ObjectNotFoundException("User not found."));

        repository.findByCpf(userDTO.getCpf()).filter(u -> !u.getId().equals(user.getId()))
                .ifPresent(u -> { throw new UserAlreadyExistsException("CPF already exists."); });

        if (findByEmail(userDTO.getEmail()) != null) {
            throw new UserAlreadyExistsException("Email already exists.");
        }
        userDTO.setPassword(encoder.encode(userDTO.getPassword()));

        User updatedUser = mapper.map(userDTO, User.class);//preserve original password
        return mapper.map(repository.save(updatedUser), User.class);
    }

    public void delete(Long id){
        findById(id);
        repository.deleteById(id);
    }

}
