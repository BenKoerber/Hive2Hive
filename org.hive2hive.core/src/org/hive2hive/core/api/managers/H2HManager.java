package org.hive2hive.core.api.managers;

import org.hive2hive.core.H2HConstants;
import org.hive2hive.core.api.interfaces.IManager;
import org.hive2hive.core.network.NetworkManager;
import org.hive2hive.core.processes.framework.exceptions.InvalidProcessStateException;
import org.hive2hive.core.processes.framework.interfaces.IProcessComponent;

/**
 * Abstract base class for all managers of the Hive2Hive project.
 * 
 * @author Christian
 * 
 */
public abstract class H2HManager implements IManager {

	protected final NetworkManager networkManager;

	// stores if autostart is enabled for this manager
	private boolean isAutostart = H2HConstants.DEFAULT_AUTOSTART_PROCESSES;

	/**
	 * Create a new manager.
	 * 
	 * @param networkManager the network manager of the current node
	 */
	protected H2HManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	/**
	 * If autostart is enabled, start the process, else ignore it.
	 * 
	 * @param processComponent the process component to submit
	 */
	protected void submitProcess(IProcessComponent processComponent) {
		if (isAutostart) {
			executeProcess(processComponent);
		}
	}

	/**
	 * Start the execution of a process.
	 * 
	 * @param processComponent the component to execute
	 */
	protected void executeProcess(IProcessComponent processComponent) {
		try {
			processComponent.start();
		} catch (InvalidProcessStateException e) {
			// should not happen
			e.printStackTrace();
		}
	}

	/**
	 * Returns the network manager.
	 * 
	 * @return the network manager
	 */
	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	@Override
	public void configureAutostart(boolean autostart) {
		this.isAutostart = autostart;
	}

	@Override
	public boolean isAutostart() {
		return isAutostart;
	}
}