//package com.fatec.starvingless.config;
//
//import com.fatec.starvingless.StarvinglessApplication;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.cloud.firestore.Firestore;
//import com.google.cloud.firestore.FirestoreOptions;
//import com.google.firebase.FirebaseApp;
//import com.google.firebase.FirebaseOptions;
//import org.springframework.boot.SpringApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.Objects;
//
//@Configuration
//public class FirestoreConfig {
//
//    @Bean
//    public Firestore firestore() throws IOException {
//
//        ClassLoader classLoader = StarvinglessApplication.class.getClassLoader();
//
//        FileInputStream serviceAccount =
//                new FileInputStream("src/main/resources/serviceAccountKey.json");
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .setDatabaseUrl("https://fir-db-for-spring-boot-7f5b6-default-rtdb.firebaseio.com")
//                .build();
//
////        if(FirebaseApp.getApps().isEmpty()) { //<------- Here
////            FirebaseApp.initializeApp(options);
////        }
//
//        FirebaseApp.initializeApp(options);
//        return FirestoreOptions.newBuilder().build().getService();
//
//    }
//
//
//    }