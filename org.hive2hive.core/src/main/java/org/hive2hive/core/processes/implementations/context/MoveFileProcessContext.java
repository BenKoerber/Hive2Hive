package org.hive2hive.core.processes.implementations.context;

import java.io.File;
import java.security.KeyPair;
import java.util.HashSet;
import java.util.Set;

import org.hive2hive.core.model.MetaFile;
import org.hive2hive.core.processes.implementations.context.interfaces.IConsumeMetaFile;
import org.hive2hive.core.processes.implementations.context.interfaces.IConsumeNotificationFactory;
import org.hive2hive.core.processes.implementations.context.interfaces.IConsumeProtectionKeys;
import org.hive2hive.core.processes.implementations.context.interfaces.IProvideMetaFile;
import org.hive2hive.core.processes.implementations.context.interfaces.IProvideNotificationFactory;
import org.hive2hive.core.processes.implementations.context.interfaces.IProvideProtectionKeys;
import org.hive2hive.core.processes.implementations.notify.BaseNotificationMessageFactory;
import org.hive2hive.core.security.HybridEncryptedContent;

public class MoveFileProcessContext implements IProvideMetaFile, IConsumeMetaFile, IProvideProtectionKeys,
		IConsumeProtectionKeys {

	private final File source;
	private final File destination;

	private KeyPair protectionKeys;
	private MetaFile metaFile;
	private KeyPair fileNodeKeys;

	private final HashSet<String> usersToNotifySource;
	private final HashSet<String> usersToNotifyDestination;

	// three context objects because we need to send three different types of notifications:
	// 1. users that had access before and after move
	// 2. users that don't have access anymore
	// 3. users that newly have access
	private final MoveNotificationContext moveNotificationContext;
	private final DeleteNotificationContext deleteNotificationContext;
	private final AddNotificationContext addNotificationContext;

	public MoveFileProcessContext(File source, File destination, String userId) {
		this.source = source;
		this.destination = destination;
		this.usersToNotifySource = new HashSet<String>();
		usersToNotifySource.add(userId);
		this.usersToNotifyDestination = new HashSet<String>();
		usersToNotifyDestination.add(userId);

		moveNotificationContext = new MoveNotificationContext();
		deleteNotificationContext = new DeleteNotificationContext();
		addNotificationContext = new AddNotificationContext();
	}

	public File getSource() {
		return source;
	}

	public File getDestination() {
		return destination;
	}

	@Override
	public KeyPair consumeProtectionKeys() {
		return protectionKeys;
	}

	@Override
	public void provideProtectionKeys(KeyPair protectionKeys) {
		this.protectionKeys = protectionKeys;

	}

	@Override
	public MetaFile consumeMetaFile() {
		return metaFile;
	}

	@Override
	public void provideMetaFile(MetaFile metaFile) {
		this.metaFile = metaFile;
	}

	@Override
	public void provideEncryptedMetaFile(HybridEncryptedContent encryptedMetaDocument) {
		// ignore because only used for deletion
	}

	public void setFileNodeKeys(KeyPair fileNodeKeys) {
		this.fileNodeKeys = fileNodeKeys;
	}

	public KeyPair getFileNodeKeys() {
		return fileNodeKeys;
	}

	public MoveNotificationContext getMoveNotificationContext() {
		return moveNotificationContext;
	}

	public DeleteNotificationContext getDeleteNotificationContext() {
		return deleteNotificationContext;
	}

	public AddNotificationContext getAddNotificationContext() {
		return addNotificationContext;
	}

	/**
	 * for users having before and after access to the file
	 */
	public class MoveNotificationContext implements IConsumeNotificationFactory, IProvideNotificationFactory {
		private BaseNotificationMessageFactory messageFactory;
		private Set<String> users;

		@Override
		public BaseNotificationMessageFactory consumeMessageFactory() {
			return messageFactory;
		}

		@Override
		public Set<String> consumeUsersToNotify() {
			return users;
		}

		@Override
		public void provideMessageFactory(BaseNotificationMessageFactory messageFactory) {
			this.messageFactory = messageFactory;
		}

		@Override
		public void provideUsersToNotify(Set<String> users) {
			this.users = users;
		}
	}

	/**
	 * for users not having access anymore
	 */
	public class DeleteNotificationContext implements IConsumeNotificationFactory,
			IProvideNotificationFactory {
		private BaseNotificationMessageFactory messageFactory;
		private Set<String> users;

		@Override
		public BaseNotificationMessageFactory consumeMessageFactory() {
			return messageFactory;
		}

		@Override
		public Set<String> consumeUsersToNotify() {
			return users;
		}

		@Override
		public void provideMessageFactory(BaseNotificationMessageFactory messageFactory) {
			this.messageFactory = messageFactory;
		}

		@Override
		public void provideUsersToNotify(Set<String> users) {
			this.users = users;
		}
	}

	/**
	 * for users now having access
	 */
	public class AddNotificationContext implements IConsumeNotificationFactory, IProvideNotificationFactory {
		private BaseNotificationMessageFactory messageFactory;
		private Set<String> users;

		@Override
		public BaseNotificationMessageFactory consumeMessageFactory() {
			return messageFactory;
		}

		@Override
		public Set<String> consumeUsersToNotify() {
			return users;
		}

		@Override
		public void provideMessageFactory(BaseNotificationMessageFactory messageFactory) {
			this.messageFactory = messageFactory;
		}

		@Override
		public void provideUsersToNotify(Set<String> users) {
			this.users = users;
		}
	}

}
