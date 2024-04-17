package opp.mic.payroll.service;

import opp.mic.payroll.impl.PhotoStorageServiceImpl;
import opp.mic.payroll.model.AppUser;
import opp.mic.payroll.model.AppImageDetails;
import opp.mic.payroll.repository.AppImageDetailsRepository;
import opp.mic.payroll.repository.UserRepository;
import opp.mic.payroll.util.PhotoStorageLocation;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AppImageDetailsService {


    private UserRepository userRepository;
    private AppImageDetailsRepository appImageDetailsRepository;
    private PhotoStorageServiceImpl photoStorageService;
    private PhotoStorageLocation storageLocation;


    public AppImageDetailsService(UserRepository userRepository, AppImageDetailsRepository appImageDetailsRepository,
                                  PhotoStorageServiceImpl photoStorageService, PhotoStorageLocation storageLocation) {
        this.userRepository = userRepository;
        this.appImageDetailsRepository = appImageDetailsRepository;
        this.photoStorageService = photoStorageService;
        this.storageLocation = storageLocation;
    }

    @Transactional
    public AppUser uploadPhoto(MultipartFile file) throws IOException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
       Optional<AppUser> appuUser = userRepository.findByUsername(username);
        if(appuUser.isPresent()) {
            AppUser user = appuUser.get();
            AppImageDetails appImageDetails = AppImageDetails.builder()
                    .path(storageLocation.getLocation() + "/" + user.getUsername() + "-" + file.getOriginalFilename())
                    .type(file.getContentType())
                    .build();
            appImageDetails.setAppUser(user);
            appImageDetailsRepository.save(appImageDetails);
            photoStorageService.store(file, user.getUsername());
            return user;
        }
        return null;
    }


    public Resource getResource(String path){
        return  photoStorageService.loadAsResource(path);
    }
}
