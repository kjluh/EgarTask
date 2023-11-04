package egar.egartask.service;

import egar.egartask.dto.PostEmployeeDto;
import egar.egartask.entites.PostEmployee;
import egar.egartask.mapper.PostEmployeeMapper;
import egar.egartask.repository.PostEmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostEmployeeRepository postEmployeeRepository;
    private final PostEmployeeMapper postEmployeeMapper;

    public PostService(PostEmployeeRepository postEmployeeRepository,
                       PostEmployeeMapper postEmployeeMapper) {
        this.postEmployeeRepository = postEmployeeRepository;
        this.postEmployeeMapper = postEmployeeMapper;
    }

    /**
     * Получение должности из бд.
     * @param id должности.
     * @return должность.
     */
    public PostEmployeeDto getPostEmployee(Long id) {
        PostEmployee postEmployee = postEmployeeRepository.findById(id).orElse(null);
        return postEmployee!= null ? postEmployeeMapper.toPostEmployeeDto(postEmployee) : null;
    }

    /**
     * Создание должности в бд.
     * @param postEmployee должность.
     * @return должность.
     */
    public PostEmployeeDto createPostEmployee(PostEmployee postEmployee) {
        if (null == postEmployeeRepository.findByPostNameContainingIgnoreCase(postEmployee.getPostName())) {
            return postEmployeeMapper.toPostEmployeeDto(postEmployeeRepository.save(postEmployee));
        } else
            return null;
    }

    /**
     * Обновление должности.
     * @param postEmployee должность.
     * @return должность.
     */
    public PostEmployeeDto updatePostEmployee(PostEmployee postEmployee) {
        if (null==postEmployeeRepository.findById(postEmployee.getId()).orElse(null)){
            return null;
        }
        return postEmployeeMapper.toPostEmployeeDto(postEmployeeRepository.save(postEmployee));
    }

    /**
     * Удаление должности.
     * @param id должности.
     * @return должность.
     */
    public PostEmployeeDto deletePostEmployee(Long id) {
        PostEmployee postEmployee = postEmployeeRepository.findById(id).orElse(null);
        if (null == postEmployeeRepository.findById(postEmployee.getId()).orElse(null)) {
            return null;
        } else {
            postEmployeeRepository.deleteById(id);
            return postEmployeeMapper.toPostEmployeeDto(postEmployee);
        }
    }
}
