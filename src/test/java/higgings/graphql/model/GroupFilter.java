package higgings.graphql.model;

import java.util.Map;

public class GroupFilter {
    public static GroupFilter fromMap(Map<String, Object> map) {
        String groupId = (String) map.get("groupId");
        String groupNameGlob = (String) map.get("nameGlob");
        String groupDescriptionGlob = (String) map.get("descGlob");
        return new GroupFilter(groupId, groupNameGlob, groupDescriptionGlob);
    }

    private final String id;
    private final String nameGlob;
    private final String descGlob;

    public GroupFilter(String id, String nameGlob, String descGlob) {
        this.id = id;
        this.nameGlob = nameGlob;
        this.descGlob = descGlob;
    }

    @Override
    public String toString() {
        return "GroupFilter{" +
                "id='" + id + '\'' +
                ", nameGlob='" + nameGlob + '\'' +
                ", descGlob='" + descGlob + '\'' +
                '}';
    }
}
