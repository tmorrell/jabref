package net.sf.jabref.model.event;

import net.sf.jabref.event.source.EntryEventSource;
import net.sf.jabref.model.entry.BibEntry;

/**
 * <code>EntryChangedEvent</code> is fired when a <code>BibEntry</code> has been changed.
 */

public class EntryChangedEvent extends EntryEvent {

    /**
     * @param bibEntry <code>BibEntry</code> object the changes were applied on.
     */
    public EntryChangedEvent(BibEntry bibEntry) {
        super(bibEntry);
    }

    /**
     * @param bibEntry <code>BibEntry</code> object the changes were applied on.
     * @param location Location affected by this event
     */
    public EntryChangedEvent(BibEntry bibEntry, EntryEventSource location) {
        super(bibEntry, location);
    }

}
