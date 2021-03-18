package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker,Integer> {
}
