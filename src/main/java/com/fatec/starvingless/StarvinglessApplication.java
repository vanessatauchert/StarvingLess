package com.fatec.starvingless;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@SpringBootApplication
public class StarvinglessApplication {

	public static void main(String[] args)  {
//
//		ClassLoader classLoader = StarvinglessApplication.class.getClassLoader();
//
//		File file = new File((Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile()));
//		FileInputStream serviceAccount =
//				new FileInputStream(file.getAbsolutePath());
//
//		FirebaseOptions options = new FirebaseOptions.Builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//				.setDatabaseUrl("https://fir-db-for-spring-boot-7f5b6-default-rtdb.firebaseio.com")
//				.build();
//
//		if(FirebaseApp.getApps().isEmpty()) { //<------- Here
//			FirebaseApp.initializeApp(options);
//		}

//
//

		SpringApplication.run(StarvinglessApplication.class, args);
	}


}
