package com.candy.sweet.service;

import com.candy.sweet.model.Account;
import com.candy.sweet.model.Candy;
import com.candy.sweet.repository.AccountRepository;
import com.candy.sweet.repository.CandyRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MainService {

    private final AccountRepository accountRepository;
    private final CandyRepository candyRepository;

    public Account getAccountByUsername(String username){
        return accountRepository.getAccountByUsername(username);
    }

    public void addCandy(Candy theCandy, MultipartFile multipartFile) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        Account owner_account = getAccountByUsername(authentication.getName());


        theCandy.setTheAccount(owner_account);



        String imageName = multipartFile.getOriginalFilename() + UUID.randomUUID();

        File file = new File("src/main/resources/static/image_base/user_"+owner_account.getId());
        if(!file.exists()){
            file.mkdir();
        }

        Path path = Paths.get(file.getPath(),"/"+ imageName);

        try {
            multipartFile.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        theCandy.setImageLink(imageName);
        candyRepository.save(theCandy);

    }

    public void saveAccount(Account theAccount) {
        accountRepository.save(theAccount);
    }

    public List<Candy> getCandyList() {
        return candyRepository.findAll();
    }


    public List<Candy> getMyCandyList(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        Account owner_account = getAccountByUsername(authentication.getName());
        System.out.println(authentication.getName());

        List<Candy> candyList = new ArrayList<>();

        for(Candy candy : candyRepository.findAll()){
            if(candy.getTheAccount().getId() == owner_account.getId()){
                candyList.add(candy);
            }
        }

        return candyList;
    }

    public Candy getCandyById(long id) {
        return candyRepository.getCandyById(id);
    }

    public void deleteCandyById(long id) {
        candyRepository.deleteById(id);
    }
}
