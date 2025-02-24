package com.example.eindopdracht;

import com.example.eindopdracht.dto.NotificationDto;
import com.example.eindopdracht.model.notification;
import com.example.eindopdracht.repository.NotificationRepository;
import com.example.eindopdracht.service.NotificationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootApplication
public class EindopdrachtNovi {

	public static void main(String[] args) {
		ApplicationContext context=SpringApplication.run(EindopdrachtNovi.class, args);

		/*NotificationRepository repo = context.getBean(NotificationRepository.class);
		notification noti = new notification();
		noti.setDescription("abc12");
		notification new1= repo.save(noti);
		System.out.println(new1);

		NotificationService NotificationService =new NotificationService(repo);
		List<NotificationDto> notifications = NotificationService.getAllNotification();
		for(NotificationDto notificationDto : notifications){
			System.out.println(notificationDto);
		}
*/


	}

}
