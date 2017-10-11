import java.util.Iterator;
import java.util.*;

public class LinkStrand implements IDnaStrand, Iterator<String> {
	
	public Node myFirst, myLast, current;
	public int myAppendsCalls;
	public long mySize;
	
	
	class Node{
		 private String value;
		   private Node next;
		   Node(String s){
			   value = s;
			   next = null;
		   }	
	}

	/**
	 * Create a strand representing an empty DNA strand, length of zero.
	 */
	public LinkStrand() {
		initialize("");
	}

	/**
	 * Create a strand representing s. No error checking is done to see if s
	 * represents valid genomic/DNA data.
	 * 
	 * @param s
	 *            is the source of cgat data for this strand
	 */
	public LinkStrand(String s) {
		initialize(s);
	}
	
	/**
	 * Initialize this strand so that it represents the value of source. No
	 * error checking is performed.
	 * 
	 * @param source
	 *            is the source of this enzyme
	 */
	@Override
	public void initialize(String source) {
		myFirst = new Node(source);
		myLast = myFirst;
		current = myFirst;
		myAppendsCalls = 0;
		mySize = source.length();
		
	}

	/**
	 * Cut this strand at every occurrence of enzyme, essentially replacing
	 * every occurrence of enzyme with splicee.
	 * 
	 * @param enzyme
	 *            is the pattern/strand searched for and replaced
	 * @param splicee
	 *            is the pattern/strand replacing each occurrence of enzyme
	 * @return the new strand leaving the original strand unchanged.
	 */
	@Override
	public IDnaStrand cutAndSplice(String enzyme, String splicee) {
		// TODO: Implement this method
		
		if(myFirst.next != null){
				throw new RuntimeException();
			}else{
				int pos = 0;
				int start = 0;
				StringBuilder val = new StringBuilder(myFirst.value);
				boolean first = true;
				LinkStrand ret = null;
				
				while ((pos = val.indexOf(enzyme, pos)) >= 0) {
					if (first) {
						ret = new LinkStrand(val.substring(start, pos));
						first = false;
					} else {
						ret.append(val.substring(start, pos));

					}
					start = pos + enzyme.length();
					ret.append(splicee);
					pos++;
				}

				if (start < val.length()) {
					if (ret == null) {
						ret = new LinkStrand("");
					} else {
						ret.append(val.substring(start));
					}
				}
				return ret;
			}
		
		}


	/**
	 * Returns the number of nucleotides/base-pairs in this strand.
	 */
	@Override
	public long size() {
		return mySize;
	}
	
	/**
	 * Return some string identifying this class.
	 * 
	 * @return a string representing this strand and its characteristics
	 */
	@Override
	public String strandInfo() {
		return this.getClass().getName();
	}

	/**
	 * Returns a string that can be printed to reveal information about what
	 * this object has encountered as it is manipulated by append and
	 * cutAndSplice.
	 * 
	 * @return
	 */
	@Override
	public String getStats() {
		return String.format("# append calls = %d", myAppendsCalls);
		
	}

	/**
	 * Returns the sequence of DNA this object represents as a String
	 * 
	 * @return the sequence of DNA this represents
	 */
	@Override
	public String toString() {
		String output = "";
		Node current = myFirst;
		while (current != null){
			output += current.value;
			current = current.next;
		}

		return output;
	}

	/**
	 * Append a strand of DNA to this strand. If the strand being appended is
	 * represented by a LinkStrand object then an efficient append is performed.
	 * 
	 * @param dna
	 *            is the strand being appended
	 */
	@Override
	public void append(IDnaStrand dna) {
		// TODO: Implement this method
		if (dna instanceof LinkStrand){
			myLast.next = (Node) dna;
			myAppendsCalls += 1;
			mySize += 1;
		}
		else{
			myLast.next = new Node(dna.toString());
			myLast = myLast.next;
			myAppendsCalls += 1;
			mySize += 1; 
		}
	}

	/**
	 * Simply append a strand of dna data to this strand.
	 * 
	 * @param dna
	 *            is the String appended to this strand
	 */
	@Override
	public void append(String dna) {
		// TODO: Implement this method
		myLast.next = new Node(dna);
		myLast = myLast.next;
		mySize += dna.length();
		myAppendsCalls += 1;
		//Node current = myFirst;
		//while (current.next != null){
			//current = current.next;
		}

	/**
	 * Returns an IDnaStrand that is the reverse of this strand, e.g., for
	 * "CGAT" returns "TAGC"
	 * 
	 * @return reverse strand
	 */
	@Override
	public IDnaStrand reverse() {
		// TODO: Implement this method
		Stack<Node> stack = new Stack<>();
		Node current = myFirst;
		while (current != null){
			String newNodeValue = "";
			for(int i = current.value.length() - 1; i >= 0; i--){
				newNodeValue += current.value.charAt(i);
			}
			current = current.next;
			Node node = new Node(newNodeValue);
			stack.push(node);
		}
		LinkStrand strand = new LinkStrand();
		while(!stack.empty()){
			strand.append(stack.pop().value);
		}
	
		return strand;
	}
	
	/**
	 *	Returns the next element in the underlying LinkedList data structure
	 */
	@Override
	public String next() {
		// TODO: Implement this method
		String val = current.value;
		current = current.next;
		return val;
		//make global variable called current that is a node 
		//in initialize set current = myFirst
	}
	
	/**
	 *	True if next evaluates correctly
	 *	False if next returns with an error
	 */
	@Override
	public boolean hasNext() {
		if(current != null){
			return true;
		}
		return false;
		
	}
}
