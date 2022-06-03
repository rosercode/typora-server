package cool.wangshuo.ts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Date:2021-08-14 08:42:07
 */

@SpringBootApplication
public class TyporaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TyporaApplication.class, args);
        System.out.println("http://127.0.0.1:8093");
    }

}
