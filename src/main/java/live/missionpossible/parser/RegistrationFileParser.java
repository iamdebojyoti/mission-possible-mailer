package live.missionpossible.parser;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import live.missionpossible.models.User;

@Component
public class RegistrationFileParser extends AbstractFileParser {

	@Override
	protected void doPostProcess(List<User> users) {
		users.forEach(user -> {
			if(StringUtils.equals(user.getEventName(),"Only Donate")) {
				user.setEventName(null);
			}
		});
	}

}
