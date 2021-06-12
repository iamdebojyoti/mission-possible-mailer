package live.missionpossible.parser;

import java.util.List;

import org.springframework.stereotype.Component;

import live.missionpossible.models.User;

@Component
public class DonationFileParser extends AbstractFileParser{

	@Override
	protected void doPostProcess(List<User> users) {
		//No-Op
	}

}
