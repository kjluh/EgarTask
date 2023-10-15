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

    /**
     * Получение должности из бд.
     * @param id должности.
     * @return должность.
     */
    public PostEmployee getPostEmployee(Long id) {
        return postEmployeeRepository.findById(id).orElse(null);
    }

    /**
     * Создание должности в бд.
     * @param postEmployee должность.
     * @return должность.
     */
    public PostEmployee createPostEmployee(PostEmployee postEmployee) {
        if (null == postEmployeeRepository.findByPostNameContainingIgnoreCase(postEmployee.getPostName())) {
            return postEmployeeRepository.save(postEmployee);
        } else
            return null;
    }

    /**
     * Обновление должности.
     * @param postEmployee должность.
     * @return должность.
     */
    public PostEmployee updatePostEmployee(PostEmployee postEmployee) {
        if (null==postEmployeeRepository.findById(postEmployee.getId()).orElse(null)){
            return null;
        }
        return postEmployeeRepository.save(postEmployee);
    }

    /**
     * Удаление должности.
     * @param id должности.
     * @return должность.
     */
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
