// CS 0445 Spring 2016
// Read this class and its comments very carefully to make sure you implement
// the class properly.  Note the items that are required and that cannot be
// altered!  Generally speaking you will implement your MyStringBuilder using
// a singly linked list of nodes.  See more comments below on the specific
// requirements for the class.

// For more details on the general functionality of most of these methods,
// see the specifications of the similar method in the StringBuilder class.
public class MyStringBuilder
{
	// These are the only three instance variables you are allowed to have.
	// See details of CNode class below.  In other words, you MAY NOT add
	// any additional instance variables to this class.  However, you may
	// use any method variables that you need within individual methods.
	// But remember that you may NOT use any variables of any other
	// linked list class or of the predefined StringBuilder or
	// or StringBuffer class in any place in your code.  You may only use the
	// String class where it is an argument or return type in a method.
	private CNode firstC;	// reference to front of list.  This reference is necessary
							// to keep track of the list
	private CNode lastC; 	// reference to last node of list.  This reference is
							// necessary to improve the efficiency of the append()
							// method
	private int length;  	// number of characters in the list

	// You may also add any additional private methods that you need to
	// help with your implementation of the public methods.

	// Create a new MyStringBuilder initialized with the chars in String s
	public MyStringBuilder(String s) {
		if (s == null || s.length() == 0) { // Special case for empty String
											// or null reference
			firstC = null;
			lastC = null;
			length = 0;
		} else {
			// Create front node to get started
			firstC = new CNode(s.charAt(0));
			length = 1;
			CNode currNode = firstC;
			// Create remaining nodes, copying from String.  Note
			// how each new node is simply added to the end of the
			// previous one.  Trace this to see what is going on.
			for (int i = 1; i < s.length(); i++) {
				CNode newNode = new CNode(s.charAt(i));
				currNode.next = newNode;
				currNode = newNode;
				length++;
			} // end for
			lastC = currNode;
		}
	} // end MyStringBuilder(String)

	// Create a new MyStringBuilder initialized with the chars in array s
	public MyStringBuilder(char [] s) {
		if (s == null || s.length == 0) { // Special case for empty array
					 			    // or null reference
			firstC = null;
			lastC = null;
			length = 0;
		} else {
			// Create front node to get started
			firstC = new CNode(s[0]);
			length = 1;
			CNode currNode = firstC;
			// Create remaining nodes, copying from String.
			for (int i = 1; i < s.length; i++) {
				CNode newNode = new CNode(s[i]);
				currNode.next = newNode;
				currNode = newNode;
				length++;
			} // end for
			lastC = currNode;
		}
	} // end MyStringBuilder(char [])

	// Create a new empty MyStringBuilder
	public MyStringBuilder() {
		// initializes MyStringBuilder to null
		firstC = null;
		lastC = null;
		length = 0;
	} // end MyStringBuilder()

	// Append MyStringBuilder b to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(MyStringBuilder b) {
		// special case if passed in object is null
		if (b == null) {
			return this;
		} else {
			// sets current node to first node of object and adds rest of nodes in
			// object
			CNode currNode = b.firstC;
			while(currNode != null) {
				append(currNode.data);
				currNode = currNode.next;
			} // end while
		}
		return this;
	} // end append(MyStringBuilder)


	// Append String s to the end of the current MyStringBuilder, and return
	// the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(String s) {
		// adds each character 1 by 1 to end of MyStringBuilder
		for (int i = 0; i < s.length(); i++) {
			append(s.charAt(i));
		} // end for
		return this;
	} // end append(String)

	// Append char array c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char [] c) {
		// adds each character 1 by 1 to end of MyStringBuilder
		for (int i = 0; i < c.length; i++) {
			append(c[i]);
		} // end for
		return this;
	} // end append(char [])

	// Append char c to the end of the current MyStringBuilder, and
	// return the current MyStringBuilder.  Be careful for special cases!
	public MyStringBuilder append(char c) {
		// special case if MyStringBuilder is empty
		if (lastC == null) {
			lastC = new CNode(c);
			firstC = lastC;
		} else {
			// adds char to end of MyStringBuilder
			lastC.next = new CNode(c);
			lastC = lastC.next;
		}
		length++;
		return this;
	} // end append(char)

	// Return the character at location "index" in the current MyStringBuilder.
	// If index is invalid, throw an IndexOutOfBoundsException.
	public char charAt(int index) {
		if (index > this.length || index < 0) {
			// throws exception if index is out of range
			throw  new IndexOutOfBoundsException();
		} else {
			CNode currNode = firstC;
			// iterates to specific index of MyStringBuilder
			for (int i = 0; i < index; i++) {
					currNode = currNode.next;
			} // end for
			return currNode.data;
		}
	} // end charAt(int)

	// Delete the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder, and return the current MyStringBuilder.
	// If "start" is invalid or "end" <= "start" do nothing (just return the
	// MyStringBuilder as is).  If "end" is past the end of the MyStringBuilder,
	// only remove up until the end of the MyStringBuilder. Be careful for
	// special cases!
	public MyStringBuilder delete(int start, int end) {
		CNode currNode = firstC;
		// special case if start is out of range
		if (start > this.length || start < 0) {
			return this;
		} else if (end <= start) {
			// special case if end is less than or equal to start
			return this;
		} else {
			// goes to specific index in MyStringBuilder
			for (int i = 0; i < start - 1; i++) {
				currNode = currNode.next;
			} // end for
			// special case if end int is larger than length of MyStringBuilder
			if (end > this.length) {
				lastC = currNode;
				lastC.next = null;
				length = start;
				// special case if start equals 0
				if (start == 0) {
					firstC = null;
					lastC = firstC;
					length = 0;
				}
			} else {
				CNode tempNode = firstC;
				for (int i = 0; i < end; i++) {
					tempNode = tempNode.next;
				}
				currNode.next = tempNode;
				// decrements length by number of nodes deleted
				length -= (end - start);
				if (start == 0) {
					firstC = tempNode;
				}
 			}
			return this;
		}
	} // end delete(int, int)

	// Delete the character at location "index" from the current
	// MyStringBuilder and return the current MyStringBuilder.  If "index" is
	// invalid, do nothing (just return the MyStringBuilder as is).
	// Be careful for special cases!
	public MyStringBuilder deleteCharAt(int index) {
		return this.delete(index, index + 1);
	} // end deleteCharAt(int)

	// Find and return the index within the current MyStringBuilder where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder.  If str does not match any sequence of characters
	// within the current MyStringBuilder, return -1.  Think carefully about
	// what you need to do for this method before implementing it.
	public int indexOf(String str) {
		CNode currNode = firstC;
		CNode seekNode = null;
		int index = 0;
		while (currNode != null) {
			seekNode = currNode;
			// matches chars of string passed in to chars in MyStringBuilder
			for (int i = 0; i < str.length(); i++) {
				if (seekNode == null || seekNode.data != str.charAt(i)) {
					// breaks out of loop if chars do not match
					break;
				}
				// returns index if chars match
				if (i == str.length() - 1) {
					return index;
				}
				// seeks next char to see if it matches
				seekNode = seekNode.next;
			} // end for
			currNode = currNode.next;
			index++;
		} // end while
		return -1;
	} // end indexOf(String)

	// Insert String str into the current MyStringBuilder starting at index
	// "offset" and return the current MyStringBuilder.  if "offset" ==
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder insert(int offset, String str) {
		CNode currNode = firstC;
		// appends if offset equals MyStringBuilder
		if (offset == this.length) {
			append(str);
		} else {
			// inserts in the beginning
			if (offset == 0) {
				for (int i = 0; i < str.length(); i++) {
					insert(offset, str.charAt(i));
					offset++;
 				} // end for
			// inserts at desired index
			} else {
				// moves pointer to desired index
				for (int i = 0; i < offset -1; i++) {
					currNode = currNode.next;
				} // end for
				// inserts string by char
				for(int i = 0; i < str.length(); i++) {
					insert(offset, str.charAt(i));
					offset++;
				} // end for
			}
		}
		return this;
 	} // end insert(int, String)

	// Insert character c into the current MyStringBuilder at index
	// "offset" and return the current MyStringBuilder.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid,
	// do nothing.
	public MyStringBuilder insert(int offset, char c) {
		CNode currNode = firstC;
		CNode tempNode = null;
		CNode insertNode = new CNode(c);
		// special case if offset is out of range
		if (offset > this.length || offset < 0) {
			return this;
		// appends if offset equals length of MyStringBuilder
		} else if (offset == this.length) {
			append(c);
		} else {
			// inserts at beginning of MyStringBuilder
			if (offset == 0) {
				tempNode = currNode;
				firstC = insertNode;
				firstC.next = tempNode;
				length++;
			// inserts at desired index
			} else {
				// moves pointer to desired index
				for (int i = 0; i < offset -1; i++) {
					currNode = currNode.next;
				} // end for
				tempNode = currNode.next;
				currNode.next = insertNode;
				insertNode.next = tempNode;
				length++;
			}
		}
		return this;
	} // end insert(int, char)

	// Insert char array c into the current MyStringBuilder starting at index
	// index "offset" and return the current MyStringBuilder.  If "offset" is
	// invalid, do nothing.
	public MyStringBuilder insert(int offset, char [] c) {
		CNode currNode = firstC;
		// appends to object if offset equals length of MyStringBuilder
		if (offset == this.length) {
			append(c);
		} else {
			// inserts to beginning if offset equals 0
			if (offset == 0) {
				// inserts each char 1 by 1
				for (int i = 0; i < c.length; i++) {
					insert(offset, c[i]);
					offset++;
 				} // end for
			// inserts at desired index
			} else {
				// points currNode to desired index
				for (int i = 0; i < offset -1; i++) {
					currNode = currNode.next;
				} // end for
				// inserts each char 1 by 1
				for(int i = 0; i < c.length; i++) {
					insert(offset, c[i]);
					offset++;
				} // end for
			}
		}
		return this;
	} // end insert(int, char [])

	// Return the length of the current MyStringBuilder
	public int length() {
		return length;
	} // end length()


	// Delete the substring from "start" to "end" - 1 in the current
	// MyStringBuilder, then insert String "str" into the current
	// MyStringBuilder starting at index "start", then return the current
	// MyStringBuilder.  If "start" is invalid or "end" <= "start", do nothing.
	// If "end" is past the end of the MyStringBuilder, only delete until the
	// end of the MyStringBuilder, then insert.
	public MyStringBuilder replace(int start, int end, String str) {
		// deletes desired nodes
		delete(start, end);
		// inserts nodes at desired position
		insert(start, str);
		return this;
	} // end replace(int, int, String)

	// Return as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder
	public String substring(int start, int end) {
		// special case if start is out of range
		if (start > this.length || start < 0) {
			return this.toString();
		// special case if end is less than or equal to start
		} else if (end <= start) {
			return this.toString();
		} else {
			String dispSubString = "";
			CNode currNode = firstC;
			// finds specific location of desired index
			for (int i = 0; i <= start - 1; i++) {
				currNode = currNode.next;
			} // end for
			// displays string char by char
			for (int i = 0; i < end - start; i++) {
				dispSubString += currNode.data;
				currNode = currNode.next;
			} // end for
			return dispSubString;
		}
	} // end substring(int, int)

	// Return the entire contents of the current MyStringBuilder as a String
	public String toString() {
		String returnVal = "";
		if(firstC != null) {
			CNode currNode = this.firstC;
			while (currNode.next != null) {
				returnVal += currNode.data;
				currNode = currNode.next;
			} // end while
			if (currNode.next == null) {
				lastC = currNode;
				returnVal += lastC.data;
			} // end if
		}
		return returnVal;
	} // end toString()

	// You must use this inner class exactly as specified below.  Note that
	// since it is an inner class, the MyStringBuilder class MAY access the
	// data and next fields directly.
	private class CNode {
		private char data;
		private CNode next;

		public CNode(char c) {
			data = c;
			next = null;
		} // end CNode(char)

		public CNode(char c, CNode n) {
			data = c;
			next = n;
		} // end CNode(char, CNode)
	} // end class CNode
} // end class MyStringBuilder
