package pt.it.av.atnog.csb.iaasmanager.client;

import org.apache.http.impl.client.DefaultHttpClient;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineCollection;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineCollectionRoot;

public class IaasManagerClient {
	private String host;
	private String user;
	private String password;
	private DefaultHttpClient client;

	public IaasManagerClient(String host, String user, String password) {
		this.host = host;
		this.user = user;
		this.password = password;
	}

	public CimiMachine createMachine() throws Exception {
		// http://localhost:8080/iaasmanager/machines
		String uri = host + "/cimi-machines";
		return postUri(CimiMachine.class, uri);
	}
	
	public CimiMachine getMachine(String id) throws Exception {
		String uri = host + "/cimi-machines/" + id;
		return getUri(CimiMachine.class, uri);
	}
	
	public CimiMachineCollectionRoot getMachines() throws Exception {
		String uri = host + "/cimi-machines";
		return getUri(CimiMachineCollectionRoot.class, uri);
	}
	
	public static String convertHrefToId(String href) {
		return href.replaceFirst(".*/([^/?]+).*", "$1");
	}

	private <T> T postUri(Class<T> returnType, String uri) throws Exception {
		ClientRequestFactory cFactory = new ClientRequestFactory();
		ClientRequest cRequest = cFactory.createRequest(uri);
		ClientResponse<T> cResponse = cRequest.post(returnType);
		return cResponse.getEntity();
	}
	
	private <T> T getUri(Class<T> returnType, String uri) throws Exception {
		ClientRequestFactory cFactory = new ClientRequestFactory();
		ClientRequest cRequest = cFactory.createRequest(uri);
		ClientResponse<T> cResponse = cRequest.get(returnType);
		return cResponse.getEntity();
	}
	
	public static void main(String[] args) throws Exception {
		IaasManagerClient client = new IaasManagerClient("http://localhost:8080/iaasmanager/rest", "admin", "admin"); // FIXME
		CimiMachineCollection cmc = client.getMachines();
		System.out.println("Machines: " + cmc.getCollection().size());
	}
}
