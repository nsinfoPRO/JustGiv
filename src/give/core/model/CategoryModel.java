package give.core.model;

import give.base.model.BaseModel;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;

public class CategoryModel extends BaseModel {

    protected String name;
    protected String imageUrl;

    public CategoryModel() {
        super();
    }

    public CategoryModel(ResultSet rs) {
        super(rs);

        this.name       = (String)this.getValueFrom(rs, "name", "");
        this.imageUrl   = (String)this.getValueFrom(rs, "image", "");
    }

    public String getName() { return this.name; }
    public void setName(String strVal) { this.name = strVal; }

    public String getImageUrl() { return this.imageUrl; }
    public void setImageUrl(String strVal) { this.imageUrl = strVal; }

    @Override
    public ArrayList<Object> toArrayForInsert() {
        ArrayList<Object> arr = new ArrayList<>();

        arr.add(name);
        arr.add(imageUrl);

        return arr;
    }

    @Override
    public ArrayList<Object> toArrayForUpdate() {
        ArrayList<Object> arr = new ArrayList<>();

        arr.add(name);
        arr.add(imageUrl);

        arr.add(m_nID);

        return arr;
    }

    public static Comparator<CategoryModel> IDComparator = new Comparator<CategoryModel>() {

        public int compare(CategoryModel s1, CategoryModel s2) {
            if(s1.getID() > s2.getID()) return 1;

            if(s1.getID() < s2.getID()) return -1;

            return 0;
        }
    };
}
