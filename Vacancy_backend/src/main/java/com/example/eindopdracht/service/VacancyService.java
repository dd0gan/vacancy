/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Service class: This basically carrying out business logic and encapsulate the app's functionality.
 */
package com.example.eindopdracht.service;

import com.example.eindopdracht.dto.*;
import com.example.eindopdracht.exception.InvalidApplicationException;
import com.example.eindopdracht.exception.InvalidVacancyException;
import com.example.eindopdracht.model.Application;
import com.example.eindopdracht.model.User;
import com.example.eindopdracht.model.Vacancy;
import com.example.eindopdracht.model.notification;
import com.example.eindopdracht.repository.ApplicationRepository;
import com.example.eindopdracht.repository.NotificationRepository;
import com.example.eindopdracht.repository.VacancyRepository;
import com.example.eindopdracht.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final ApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;

    public VacancyService(VacancyRepository vacancyRepository, ApplicationRepository applicationRepository,NotificationRepository notificationRepository) {
        this.vacancyRepository = vacancyRepository;
        this.applicationRepository = applicationRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<VacancyDto> getAllVacancies() {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Vacancy> vacancies = new ArrayList<>();
        System.out.println("find vacancy" + user.getUsername());

        if(user != null && user.getRoleName().equals("ADMIN")) {
            vacancies=vacancyRepository.findByUsername(user.getUsername());
        }
        else
        {
            vacancyRepository.findAll().iterator().forEachRemaining(vacancies::add);
        }
        return vacancies.stream().map(this::getVacancyDto).collect(Collectors.toList());
    }
    /*public List<VacancyDto> filterBy(String username) {
        List<Vacancy> notifications =vacancyRepository.findByUsername(username);

        List<Vacancy> vacancies = vacancyRepository.findByUsername((username));
        return vacancies.stream().map(this::getVacancyDto).collect(Collectors.toList());

        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<Vacancy> v =vacancyRepository.findByUsername(username);
        return notifications.stream().map(this::getNotificationDto).collect(Collectors.toList());

    }*/

    public VacancyDto createVacancy(VacancyDto v) {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        Vacancy vacancy = new Vacancy(v.getId(),  v.getTitle(), v.getSkills(),  v.getDescription(), v.getHourlyRate(), v.getWorkingHour(), v.getType(), v.getLocation(), v.getStatus(),user.getUsername());
        vacancy = vacancyRepository.save(vacancy);
        VacancyDto result = getVacancyDto(vacancy);

        // save notification
        NotificationDto objNoti=new NotificationDto(user.getUsername(),"you have successfully post new vacany title " + vacancy.getTitle());
        new NotificationService(notificationRepository).createNotification(objNoti);

        return result;
    }

    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        if (vacancyDto.getId() == null) {
            throw new InvalidVacancyException("Invalid vacancy id is supplied");
        }

        Optional<Vacancy> vo = vacancyRepository.findById(vacancyDto.getId());

        Vacancy vacancy = vo.orElseThrow(() -> new InvalidVacancyException("Vacancy not found"));
        vacancy.setSkills(vacancyDto.getSkills());
        vacancy.setOrganization(vacancyDto.getOrganization());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setHourlyRate(vacancyDto.getHourlyRate());
        vacancy.setWorkingHour(vacancyDto.getWorkingHour());
        vacancy.setType(vacancyDto.getType());
        vacancy.setLocation(vacancyDto.getLocation());
        vacancy.setStatus(Vacancy.VacancyStatus.valueOf(vacancyDto.getStatus()));
        vacancyRepository.save(vacancy);
        VacancyDto result = getVacancyDto(vacancy);

        // save notification
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        NotificationDto objNoti=new NotificationDto(user.getUsername(),"you have successfully updated vacany title " + vacancy.getTitle());
        new NotificationService(notificationRepository).createNotification(objNoti);

        return result;
    }

    private VacancyDto getVacancyDto(Vacancy vacancy) {
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        VacancyDto result = new VacancyDto(vacancy.getId(),vacancy.getTitle(), user.getUsername(), vacancy.getSkills(), vacancy.getOrganization(), vacancy.getDescription(), vacancy.getHourlyRate(), vacancy.getWorkingHour(), vacancy.getType(), vacancy.getLocation(), vacancy.getStatus().toString());
        List<Application> applications = applicationRepository.findByVacancyId(vacancy.getId());
        for (Application application : applications) {
            ApplicationDto applicationDto = new ApplicationDto();
            applicationDto.setId(application.getId());
            applicationDto.setStatus(application.getStatus().toString());

            UserDto userDto = new UserDto();
            userDto.setUsername(application.getEmployee().getUsername());
            userDto.setCvUniqueId(application.getEmployee().getCvUniqueId());
            userDto.setCvFilename(application.getEmployee().getCvFilename());
            applicationDto.setUser(userDto);
            result.getApplications().add(applicationDto);
        }
        return result;
    }

    public MessageDto apply(Integer id) {
        if (id == null) {
            throw new InvalidVacancyException("Invalid vacancy id is supplied");
        }
        Optional<Vacancy> vo = vacancyRepository.findById(id);
        Vacancy vacancy = vo.orElseThrow(() -> new InvalidVacancyException("Vacancy not found"));

        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        boolean isApplied = vacancy.getApplications().stream().anyMatch(ap -> ap.getVacancy().getId().equals(vacancy.getId()) && ap.getEmployee().getUsername().equals(user.getUsername()));

        if (isApplied) {
            throw new InvalidApplicationException("Already applied");
        }

        if (Vacancy.VacancyStatus.CLOSE.equals(vacancy.getStatus())) {
            throw new InvalidApplicationException("Already closed");
        }

        Application application = new Application();
        application.setVacancy(vacancy);
        application.setEmployee(user);
        application.setStatus(Application.ApplicationStatus.PENDING);
        applicationRepository.save(application);

        // save notification
        NotificationDto objNoti=new NotificationDto(user.getUsername(),"you have successfully applied to the job " + vacancy.getTitle());
        new NotificationService(notificationRepository).createNotification(objNoti);

        return new MessageDto("Applied successfully");
    }

    public VacancyDto getVacancy(Integer id) {
        if (id == null) {
            throw new InvalidVacancyException("Invalid vacancy id is supplied");
        }
        Optional<Vacancy> vo = vacancyRepository.findById(id);
        Vacancy vacancy = vo.orElseThrow(() -> new InvalidVacancyException("Vacancy not found"));
        return getVacancyDto(vacancy);
    }

    public MessageDto complete(Integer id, Integer applicationId, String acceptReject) {

        // show error message if invalid selection done or empty data
        if (id == null)
        {
            throw new InvalidVacancyException("Invalid vacancy id is supplied");
        }
        if (applicationId == null) {
            throw new InvalidApplicationException("Invalid application id is supplied");
        }

        Optional<Vacancy> vo = vacancyRepository.findById(id);
        Vacancy vacancy = vo.orElseThrow(() -> new InvalidVacancyException("Vacancy not found"));

        Optional<Application> ao = applicationRepository.findById(applicationId);
        Application application = ao.orElseThrow(() ->  new InvalidApplicationException("Application not found"));

        application.setStatus(Application.ApplicationStatus.valueOf(acceptReject));
        applicationRepository.save(application);

        // save notification
        User user = ((MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        NotificationDto objNoti1=new NotificationDto(user.getUsername(),"you have "+acceptReject+" a candidate for vacancy name " + vacancy.getTitle());
        new NotificationService(notificationRepository).createNotification(objNoti1);

        String employeeNotfication="";
        if(acceptReject.equals("REJECTED")){
            employeeNotfication="Your request has been rejected for vacany name : " + vacancy.getTitle();
        }
        else
        {
            employeeNotfication= "Congratulations! You have been selected for the vacancy name : " + vacancy.getTitle() + "<br> Further employers will contact you for the final interview..!";
        }
        NotificationDto objNoti2=new NotificationDto(application.getEmployee().getUsername(),employeeNotfication);
        new NotificationService(notificationRepository).createNotification(objNoti2);

        return new MessageDto("Request successfully " + acceptReject);
    }
}
