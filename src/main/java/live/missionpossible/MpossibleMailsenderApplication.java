package live.missionpossible;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import live.missionpossible.mail.MailSenderService;
import live.missionpossible.types.AppMode;

@SpringBootApplication
@ComponentScan("live.missionpossible")
public class MpossibleMailsenderApplication implements CommandLineRunner {

	@Autowired
	private MailSenderService mailSenderService;

	@Value("${filename}")
	private String fileName;

	@Value("${mode}")
	private String mode;

	@Value("${sheetName}")
	private String sheetName;

	public static void main(String[] args) {
		SpringApplication.run(MpossibleMailsenderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (StringUtils.isEmpty(fileName) || StringUtils.isEmpty(mode) || StringUtils.isEmpty(sheetName)) {
			System.out.println("[ERROR] Either filename or mode or sheetname is not provided");
		} else {
			AppMode appMode = AppMode.get(mode);
			if (appMode == null) {
				System.out.println("[ERROR] Appmode should be DONATION or REGISTRATION");
			} else {
				mailSenderService.send(fileName, sheetName, appMode);
			}
		}
	}
}
