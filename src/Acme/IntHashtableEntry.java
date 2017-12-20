package Acme;


// Referenced classes of package Acme:
//			IntHashtable, IntHashtableEnumerator

class IntHashtableEntry {

	int hash;
	int key;
	Object value;
	IntHashtableEntry next;

	IntHashtableEntry() {
	}

	protected Object clone() {
		IntHashtableEntry inthashtableentry = new IntHashtableEntry();
		inthashtableentry.hash = hash;
		inthashtableentry.key = key;
		inthashtableentry.value = value;
		inthashtableentry.next = next == null ? null : (IntHashtableEntry)next.clone();
		return inthashtableentry;
	}
}
