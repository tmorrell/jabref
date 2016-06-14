package net.sf.jabref.importer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import net.sf.jabref.gui.BasePanel;
import net.sf.jabref.logic.groups.ExplicitGroup;
import net.sf.jabref.logic.groups.GroupTreeNode;
import net.sf.jabref.model.entry.BibEntry;

/**
 * Converts legacy explicit groups, where the group contained a list of assigned entries, to the new format,
 * where the entry stores a list of groups it belongs to.
 */
public class ConvertLegacyExplicitGroups implements PostOpenAction {

    @Override
    public boolean isActionNecessary(ParserResult pr) {
        if(pr.getMetaData().getGroups() == null) {
            return false;
        }
        return !getExplicitGroupsWithLegacyKeys(pr.getMetaData().getGroups()).isEmpty();
    }

    @Override
    public void performAction(BasePanel panel, ParserResult pr) {
        Objects.requireNonNull(pr);
        GroupTreeNode groupsTree = pr.getMetaData().getGroups();
        if(groupsTree == null) {
            return;
        }

        for (ExplicitGroup group : getExplicitGroupsWithLegacyKeys(groupsTree)) {
            List<String> elements = new LinkedList<>();

            for (String entryKey : group.getLegacyEntryKeys()) {
                for (BibEntry entry : pr.getDatabase().getEntriesByKey(entryKey)) {
                    group.add(entry);
                }
            }
            group.clearLegacyEntryKeys();
        }
    }

    private List<ExplicitGroup> getExplicitGroupsWithLegacyKeys(GroupTreeNode node) {
        Objects.requireNonNull(node);
        List<ExplicitGroup> findings = new ArrayList<>();

        if (node.getGroup() instanceof ExplicitGroup) {
            ExplicitGroup group = (ExplicitGroup) node.getGroup();
            if (!group.getLegacyEntryKeys().isEmpty()) {
                List<GroupTreeNode> parents = getGroupPath(node);
                String search = parents.stream().map(g -> g.getName()).collect(Collectors.joining(">"));
                group.setName(search);
                group.setSearchExpression(search);
                findings.add(group);
            }
        }

        node.getChildren().forEach(child -> findings.addAll(getExplicitGroupsWithLegacyKeys(child)));

        return findings;
    }

    private List<GroupTreeNode> getGroupPath(GroupTreeNode node) {
        List<GroupTreeNode> parents = new LinkedList<>();
        node.getParent().ifPresent(g -> {
            parents.addAll(getGroupPath(g));
        });
        parents.add(node);
        return parents;
    }
}
