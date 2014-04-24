package org.hive2hive.client.menu;

import org.hive2hive.client.ConsoleClient;
import org.hive2hive.client.console.H2HConsoleMenu;
import org.hive2hive.client.console.H2HConsoleMenuItem;
import org.hive2hive.client.util.MenuContainer;
import org.hive2hive.core.security.UserCredentials;

/**
 * The user configuration menu of the {@link ConsoleClient}.
 * 
 * @author Christian, Nico
 * 
 */
public final class UserMenu extends H2HConsoleMenu {

	public H2HConsoleMenuItem CreateUserCredentials;

	private UserCredentials userCredentials;

	public UserMenu(MenuContainer menus) {
		super(menus);
	}

	@Override
	protected void createItems() {
		CreateUserCredentials = new H2HConsoleMenuItem("Create User Credentials") {
			protected void execute() throws Exception {
				userCredentials = new UserCredentials(askUserId(), askPassword(), askPin());
				exit();
			}
		};
	}

	@Override
	protected void addMenuItems() {
		add(CreateUserCredentials);
	}

	@Override
	protected String getInstruction() {
		return "Please select a user configuration option:";
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	private String askUserId() {
		print("Specify the user ID:");
		return awaitStringParameter().trim();
	}

	private String askPassword() {
		print("Specify the user password:");
		return awaitStringParameter().trim();
	}

	private String askPin() {
		print("Specify the user PIN:");
		return awaitStringParameter().trim();
	}

	public void forceUserCredentials() {
		while (getUserCredentials() == null) {
			CreateUserCredentials.invoke();
		}
	}
}
