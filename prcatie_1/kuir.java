package prcatie_1;

public class kuir {

	public static void main(String[] args) throws Exception{
		
		String command = args[0];   
		String path = args[1];


		if(command.equals("-c")) {
			makeCollection collection = new makeCollection(path);
			collection.makeXml();
		}
		else if(command.equals("-k")) {
			makeKeyword keyword = new makeKeyword(path);
			keyword.convertXml();
		}
		else if(command.equals("-i")) {
			indexer post = new indexer(path);
			post.makePost();
			post.ReadPost();
		}

		if(command.equals("-s")) {
			String command2 = args[2];
			String query = args[3];
			searcher search = new searcher(path);
			if(command2.equals("-q")) {
			search.Innerproduct(query);
			}

		}
	}
}