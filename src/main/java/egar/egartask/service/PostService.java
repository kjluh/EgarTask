package egar.egartask.service;

import egar.egartask.entites.PostEmployee;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostEmployeeRepository postEmployeeRepository;

    public PostService(PostEmployeeRepository postEmployeeRepository) {
        this.postEmployeeRepository = postEmployeeRepository;
    }

    public PostEmployee getPostEmployee(Long id) {
        return postEmployeeRepository.findById(id).orElse(null);
    }

    public PostEmployee createPostEmployee(PostEmployee postEmployee) {
        if (null == postEmployeeRepository.findByPostNameContainingIgnoreCase(postEmployee.getPostName())) {
            return postEmployeeRepository.save(postEmployee);
        } else
            return null;
    }

    public PostEmployee updatePostEmployee(PostEmployee postEmployee) {
        if (null==postEmployeeRepository.findById(postEmployee.getId()).orElse(null)){
            return null;
        }
        return postEmployeeRepository.save(postEmployee);
    }

    public PostEmployee deletePostEmployee(Long id) {
        PostEmployee postEmployee = postEmployeeRepository.findById(id).orElse(null);
        if (null == postEmployeeRepository.findById(postEmployee.getId()).orElse(null)) {
            return null;
        } else {
            postEmployeeRepository.deleteById(id);
            return postEmployee;
        }
    }
}
