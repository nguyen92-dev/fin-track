package top.nguyennd.expense;

import org.springframework.boot.SpringApplication;

public class TestExpenseApplication {

    static void main(String[] args) {
        SpringApplication.from(ExpenseApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
