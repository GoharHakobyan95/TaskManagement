package am.itspace.taskmangement.service;

import am.itspace.taskmangement.entity.Role;
import am.itspace.taskmangement.entity.Task;
import am.itspace.taskmangement.entity.User;
import am.itspace.taskmangement.repository.TaskRepository;
import am.itspace.taskmangement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;


    public Page<Task> findTaskByUserRole(User user, Pageable pageable) {
        List<Task> all = taskRepository.findAll();
        return user.getRole() == Role.USER ? taskRepository.findAllByUser_id(user.getId(), pageable)
                : taskRepository.findAll(pageable);
    }

    public void saveNewTask(Task task) {
        if (task.getUser() != null && task.getUser().getId() == 0) {
            task.setUser(null);
        }
        taskRepository.save(task);
    }

    public void changeTaskUser(int userId, int taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (taskOptional.isPresent() && userOptional.isPresent()) {
            Task task = taskOptional.get();
            User user = userOptional.get();
            if (task.getUser() != user) {
                task.setUser(user);
                taskRepository.save(task);
            }
        } else if (taskOptional.isPresent() && userId == 0) {
            taskOptional.get().setUser(null);
            taskRepository.save(taskOptional.get());
        }
    }
}
