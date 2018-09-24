package galileo.dht;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RikiTest {
	
	public static void main(String arg[]) throws FileNotFoundException, IOException {
		
		/*String[] abc = new String[2];
		abc[0] = "a";
		abc[1] = "b";
		
		//System.out.println(abc);
		List<String> myList = Arrays.asList(abc);
		
		System.out.println(myList.toString());
		
		String home = "/s/chopin/b/grad/sapmitra/workspace/mobius/config/network_singlering/";

		NetworkConfig.readNetworkDescription(home);*/
		
		String[] ss = "hello$$world".split("\\$\\$");
		
		System.out.println(ss[0]);
		System.out.println(ss[1]);
		
	}

}
