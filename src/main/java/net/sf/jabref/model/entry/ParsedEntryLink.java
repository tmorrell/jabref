package net.sf.jabref.model.entry;

import java.util.Optional;

import net.sf.jabref.model.database.BibDatabase;

public class ParsedEntryLink {

    private String key;
    private Optional<BibEntry> linkedEntry;
    private BibDatabase dataBase;

    public ParsedEntryLink(String key, BibDatabase dataBase) {
        this.key = key;
        this.linkedEntry = dataBase.getEntryByKey(this.key);
        this.dataBase = dataBase;
    }

    public ParsedEntryLink(String key, BibEntry bibEntry) {
        this.key = key;
        this.linkedEntry = Optional.of(bibEntry);
    }

    public String getKey() {
        return key;
    }

    public Optional<BibEntry> getLinkedEntry() {
        return linkedEntry;
    }

    public void setKey(String newKey) {
        this.key = newKey;
        this.linkedEntry = dataBase.getEntryByKey(this.key);
    }
}
