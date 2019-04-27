package com.xbc.contacts;

import java.util.Comparator;

/**
 * 
 * @author xiaanming
 *
 */
public class LettersComparator implements Comparator<ContactsInfo> {

	public int compare(ContactsInfo o1, ContactsInfo o2) {
		if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#")) {
			return -1;
		} else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("@")) {
			return 1;
		} else {
			return o1.sortLetters.compareTo(o2.sortLetters);
		}
	}

}
