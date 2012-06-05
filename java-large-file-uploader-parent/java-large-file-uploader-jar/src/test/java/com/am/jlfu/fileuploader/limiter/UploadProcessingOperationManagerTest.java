package com.am.jlfu.fileuploader.limiter;


import java.util.UUID;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@ContextConfiguration(locations = { "classpath:jlfu.test.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class UploadProcessingOperationManagerTest {

	@Autowired
	UploadProcessingOperationManager uploadProcessingOperationManager;



	@Before
	public void before() {
		uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.clear();
		uploadProcessingOperationManager.clientToRequestsMapping.clear();
	}


	@Test
	public void test() {

		UUID clientId = UUID.randomUUID();
		UUID fileId = UUID.randomUUID();
		UUID fileId2 = UUID.randomUUID();

		Assert.assertThat(uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.isEmpty(), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.isEmpty(), CoreMatchers.is(true));

		uploadProcessingOperationManager.startOperation(clientId, fileId);

		Assert.assertThat(uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.containsKey(clientId), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.get(clientId).contains(fileId), CoreMatchers.is(true));

		uploadProcessingOperationManager.startOperation(clientId, fileId2);

		Assert.assertThat(uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.containsKey(clientId), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.get(clientId).contains(fileId), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.get(clientId).contains(fileId2), CoreMatchers.is(true));

		uploadProcessingOperationManager.stopOperation(clientId, fileId2);

		Assert.assertThat(uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.containsKey(clientId), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.get(clientId).contains(fileId), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.get(clientId).contains(fileId2), CoreMatchers.is(false));

		uploadProcessingOperationManager.stopOperation(clientId, fileId);

		Assert.assertThat(uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.containsKey(clientId), CoreMatchers.is(false));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.containsKey(clientId), CoreMatchers.is(false));
		Assert.assertThat(uploadProcessingOperationManager.clientsAndRequestsProcessingOperation.isEmpty(), CoreMatchers.is(true));
		Assert.assertThat(uploadProcessingOperationManager.clientToRequestsMapping.isEmpty(), CoreMatchers.is(true));


	}
}
