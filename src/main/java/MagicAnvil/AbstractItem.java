package MagicAnvil;

public abstract class AbstractItem {
    protected ItemTypes itemTypes;

    public abstract String getItemTypes();

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
}
