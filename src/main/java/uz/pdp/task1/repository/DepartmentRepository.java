package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {
}
