
public class MyStringBuilder2
{
	// private variables
	private CNode firstC;	// reference to front of list.
	private CNode lastC; 	// reference to last node of list.
	private int length;  	// number of characters in the list

	// MyStringBuilder2 initialized with the chars in String s
	public MyStringBuilder2(String s) {
		if (s != null && s.length() > 0) {
    	makeBuilder(s, 0);
    } else { // no String so initialize empty MyStringBuilder2
      length = 0;
      firstC = null;
      lastC = null;
    }
	} // end MyStringBuilder2(String)

	// MyStringBuilder2 initialized with the chars in array s
	public MyStringBuilder2(char [] s) {
		if (s != null && s.length > 0) {
    	makeBuilder(s, 0);
    } else { // no String so initialize empty MyStringBuilder2
      length = 0;
      firstC = null;
      lastC = null;
    }
	} // end MyStringBuilder2(char)

	// Creates a new empty MyStringBuilder2
	public MyStringBuilder2() {
		// initializes MyStringBuilder to null
	 	firstC = null;
		lastC = null;
		length = 0;
	} // end MyStringBuilder2()

	// Recursive method to set up a new MyStringBuilder2 from a String
	private void makeBuilder(String s, int pos) {
    // Recursive case – we have not finished going through the String
    if (pos < s.length() - 1) {
	  	// Calls recursive call first to avoid having to make a special test
	  	// for the front node
      makeBuilder(s, pos + 1);
      firstC = new CNode(s.charAt(pos), firstC);
      length++;
    } else if (pos == s.length() - 1) { // Special case for last char in String
      firstC = new CNode(s.charAt(pos));
      lastC = firstC;
      length = 1;
    } else { // This case should never be reached
      length = 0;
      firstC = null;
      lastC = null;
    }
	} // end makeBuilder(String, int)

	// Recursive method to set up a new MyStringBuilder2 from a char array
	public void makeBuilder(char [] s, int pos) {
		// Recursive case – we have not finished going through the array
    if (pos < s.length - 1) {
	  	// Calls recursive call first to avoid having to make a special test
	  	// for the front node
      makeBuilder(s, pos + 1);
      firstC = new CNode(s[pos], firstC);
      length++;
    } else if (pos == s.length - 1) { // Special case for last char in String
      firstC = new CNode(s[pos]);
      lastC = firstC;
      length = 1;
    } else { // This case should never be reached
      length = 0;
      firstC = null;
      lastC = null;
    }
	} // end makeBuilder(char [], int)

	// Appends MyStringBuilder2 b to the end of the current MyStringBuilder2, and
	// returns the current MyStringBuilder2.
	public MyStringBuilder2 append(MyStringBuilder2 b) {
		// special case if passed in object is null
		if (b == null) {
			return this;
		} else {
			// sets current node to first node of object and adds rest of nodes in
			// object
		}
		return trackAppend(b, b.firstC);
	} // end append(MyStringBuilder2)

	// recursive method used to append to end of object
	public MyStringBuilder2 trackAppend(MyStringBuilder2 obj, CNode currNode) {
		if(currNode != null) {
			append(currNode.data);
			currNode = currNode.next;
			trackAppend(obj, currNode);
		}
		return this;
	} // end trackAppend(MyStringBuilder2, int)

	// Appends String s to the end of the current MyStringBuilder2, and returns
	// the current MyStringBuilder2.
	public MyStringBuilder2 append(String s) {
		return trackAppend(s, 0);
	} // end append(String)

	// recursive method used to add char to end of String
	public MyStringBuilder2 trackAppend(String obj, int count) {
		if (count < obj.length()) {
			append(obj.charAt(count));
			trackAppend(obj, count + 1);
		}
		return this;
 	} // end trackAppend(String)

	// Append char array c to the end of the current MyStringBuilder2, and
	// return the current MyStringBuilder2.  Be careful for special cases!
	public MyStringBuilder2 append(char [] c) {
		return trackAppend(c, 0);
	} // end append(char [])

	// recursive method used to add char to end of array
	public MyStringBuilder2 trackAppend(char[] obj, int count) {
		if (count < obj.length) {
			append(obj[count]);
			trackAppend(obj, count + 1);
		}
		return this;
 	} // end trackAppend(String)

	// Appends char c to the end of the current MyStringBuilder2, and
	// returns the current MyStringBuilder2.
	public MyStringBuilder2 append(char c) {
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

	// Returns the character at location "index" in the current MyStringBuilder2.
	// Throws an IndexOutOfBoundsExceptionif index is invalid.
	public char charAt(int index) {
		if (index > this.length || index < 0) {
			// throws exception if index is out of range
			throw  new IndexOutOfBoundsException();
		} else {
			return trackIndex(index, 0, firstC);
		}
	} // end charAt (int)

	// recursive method used to find specific index in obj
	public char trackIndex(int index, int count, CNode currNode) {
		if(count == index)
			return currNode.data;
		return trackIndex(index, count+1, currNode.next);
	} // end trackIndex(int, int)

	// Deletes the characters from index "start" to index "end" - 1 in the
	// current MyStringBuilder2, and return the current MyStringBuilder2.
	// If "start" is invalid or "end" <= "start", it returns the
	// MyStringBuilder2 as is.  If "end" is past the end of the MyStringBuilder2,
	// it only removes up until the end of the MyStringBuilder2.
	public MyStringBuilder2 delete(int start, int end) {
		CNode currNode = firstC;
		// special case if start is out of range
		if (start > this.length || start < 0) {
			return this;
		} else if (end <= start) {
			// special case if end is less than or equal to start
			return this;
		} else {
			// goes to specific index in MyStringBuilder
			currNode = iterateStart(start, 0, currNode);
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
				tempNode = iterateEnd(end, 0, tempNode);
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

	// recursive methods used to iterate to the specificed index
	public CNode iterateStart(int start, int count, CNode currNode) {
		if(count < start - 1) {
			return iterateStart(start, count + 1, currNode.next);
		}
		return currNode;
	} // end iterateStart(int, int, CNode)
	public CNode iterateEnd(int end, int count, CNode tempNode) {
		if (count < end) {
			return iterateEnd(end, count + 1, tempNode.next);
		}
		return tempNode;
	} // end iterateEnd(int, int, CNode)

	// Deletes the character at location "index" from the current
	// MyStringBuilder2 and returns the current MyStringBuilder2.  If "index" is
	// invalid, it returns the MyStringBuilder2 as is.
	public MyStringBuilder2 deleteCharAt(int index) {
		return this.delete(index, index + 1);
	} // end deleteCharAt(int)

	// Finds and returns the index within the current MyStringBuilder2 where
	// String str first matches a sequence of characters within the current
	// MyStringBuilder2.  If str does not match any sequence of characters
	// within the current MyStringBuilder2, this method returns -1.
	public int indexOf(String str) {
		if (firstC == null) {
			return -1;
		} else {
			return charMatch(firstC, str, 0);
		}
	} // end indexOf(String)

	// recursive method used to check if string matches in object
	public boolean stringMatch(CNode currNode, int index, String str) {
		if (index == str.length()) { return true; }
		if (currNode == null) { return false;}
		if(currNode.data != str.charAt(index)) { return false; }
		return stringMatch(currNode.next, index + 1, str);
	} // end stringMatch (int, CNode)
	// recursive method used to match char in string
	public int charMatch(CNode currNode, String str, int index) {
		if (currNode == null) { return -1; }
		if (stringMatch(currNode, 0, str)) {return index; }
		return charMatch(currNode.next, str, index + 1);
	} // end charMatch(CNode, String, int)

	// Inserts String str into the current MyStringBuilder2 starting at index
	// "offset" and return the current MyStringBuilder2.  if "offset" ==
	// length, this is the same as append.  If "offset" is invalid
	// do nothing.
	public MyStringBuilder2 insert(int offset, String str) {
		CNode currNode = firstC;
		// appends if offset equals MyStringBuilder
		if (offset == this.length) {
			append(str);
		} else {
			// inserts in the beginning
			if (offset == 0) {
				recurInsert(offset, str, 0);
			// inserts at desired index
			} else {
				// moves pointer to desired index
				currNode = iterateInsert(offset, 0, currNode);
				// inserts string by char
				recurInsert(offset, str, 0);
			}
		}
		return this;
	} // end insert(int, String)

	// Inserts character c into the current MyStringBuilder2 at index
	// "offset" and return the current MyStringBuilder2.  If "offset" ==
	// length, this is the same as append.  If "offset" is invalid,
	// do nothing.
	public MyStringBuilder2 insert(int offset, char c) {
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
				currNode = iterateInsert(offset, 0, currNode);

				tempNode = currNode.next;
				currNode.next = insertNode;
				insertNode.next = tempNode;
				length++;
			}
		}
		return this;
	} // end insert(int, char)

	// Inserts char array c into the current MyStringBuilder2 starting at index
	// index "offset" and return the current MyStringBuilder2.  If "offset" is
	// invalid, it does nothing.
	public MyStringBuilder2 insert(int offset, char [] c) {
		CNode currNode = firstC;
		// appends if offset equals MyStringBuilder
		if (offset == this.length) {
			append(c);
		} else {
			// inserts in the beginning
			if (offset == 0) {
				recurInsert(offset, c, 0);
			// inserts at desired index
			} else {
				// moves pointer to desired index
				currNode = iterateInsert(offset, 0, currNode);
				// inserts string by char
				recurInsert(offset, c, 0);
			}
		}
		return this;
	} // end insert(int, char[])

	// recursive method used to iterate through the object
	public CNode iterateInsert(int offset, int count, CNode currNode) {
		if (count < offset - 1) {
			return iterateInsert(offset, count + 1, currNode.next);
		}
		return currNode;
	} // end iterateReplace(int, int, CNode)

	// recursive method used to iterate through the string
	public void recurInsert(int offset, String str, int count) {
		if (count < str.length()) {
			insert(offset, str.charAt(count));
			recurInsert(offset + 1, str, count + 1);
		}
	} // end iterateStr(int, String, int)

	// recursive method used to iterate through the array
	public void recurInsert(int offset, char[] c, int count) {
		if (count < c.length) {
			insert(offset, c[count]);
			recurInsert(offset + 1, c, count + 1);
		}
	} // end iterateStr(int, String, int)

	// Return the length of the current MyStringBuilder2
	public int length() {
		return length;
	} // end length()

	// Deletes the substring from "start" to "end" - 1 in the current
	// MyStringBuilder2, then inserts String "str" into the current
	// MyStringBuilder2 starting at index "start", then returns the current
	// MyStringBuilder2.  If "start" is invalid or "end" <= "start", it dooes
	// nothing. If "end" is past the end of the MyStringBuilder2, it deletes
	// until the end of the MyStringBuilder2, and thens insert.
	public MyStringBuilder2 replace(int start, int end, String str) {
		// deletes desired nodes
		delete(start, end);
		// inserts nodes at desired position
		insert(start, str);
		return this;
	} // end replace(int, int, String)

	// Returns as a String the substring of characters from index "start" to
	// index "end" - 1 within the current MyStringBuilder2
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
			currNode = iterateStartPos(start, 0, currNode);
			// displays string char by char
			return displayStr(start, end, 0, currNode, dispSubString);
		}
	} // end substring(int, int)

	// recursive method used to iterate through the object
	public CNode iterateStartPos(int start, int count, CNode currNode) {
		if (count < start) {
			return iterateStartPos(start, count + 1, currNode.next);
		}
		return currNode;
	} // end iterateReplace(int, int, CNode)

	// recursive method used to display substring
	public String displayStr(int start, int end, int count, CNode currNode, String dispSubString) {
		if (count < (end - start)) {
			dispSubString += currNode.data;
			return displayStr(start, end, count + 1, currNode.next, dispSubString);
		}
		return dispSubString;
	} // end displayStr(int, int, int, CNode, String)

	// Returns the entire contents of the current MyStringBuilder2 as a String
	public String toString() {
		char [] c = new char[length];
  	getString(c, 0, firstC);
  	return (new String(c));
 	} // end toString()

  // recursive method to get String for toString() method
	private void getString(char [] c, int pos, CNode curr) {
  	if (curr != null) {
    	c[pos] = curr.data;
      getString(c, pos+1, curr.next);
    }
	} // end getString(char, int, CNode)


	// Inner class that can be accessed through MyStringBuilder2
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
} // end class MyStringBuilder2
