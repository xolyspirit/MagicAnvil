package MagicAnvil;
/**Базовый класс для всех вещей**/
public abstract class AbstractItem {
    //тип премета- мебель, доспех, оружие и тд...
    protected ItemTypes itemTypes;
    //описание предмета
    protected String description;
    //основной цвет
    protected Material.Colors mainColor;
    //дополнительный цвет
    protected Material.Colors additionalColor;
    //уровень качества предмета
    protected Material.Quality quality;

/**какие могут быть вещи в принципе. для каждого типа будет своя реализация**/
    public enum ItemTypes {
        EQUIPMENT,
        FURNITURE,
        TOOL,
        MATERIAL;

        public String toString() {
            String s = "";
            switch (this) {
                case EQUIPMENT: s = "Снаряжение";
                    break;
                case FURNITURE: s = "Мебель";
                    break;
                case TOOL: s = "Инструмент";
                    break;
                case MATERIAL: s = "Материал";
                    break;
            }
            return s;
        }
    }

    //стандартные геттеры сеттеры

    public Material.Quality getQuality() {
        return quality;
    }

    public void setQuality(Material.Quality quality) {
        this.quality = quality;
    }

    public ItemTypes getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(ItemTypes itemTypes) {
        this.itemTypes = itemTypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Material.Colors getMainColor() {
        return mainColor;
    }

    public void setMainColor(Material.Colors mainColor) {
        this.mainColor = mainColor;
    }

    public Material.Colors getAdditionalColor() {
        return additionalColor;
    }

    public void setAdditionalColor(Material.Colors additionalColor) {
        this.additionalColor = additionalColor;
    }
}
