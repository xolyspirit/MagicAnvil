package MagicAnvil;

public abstract class Equipment extends AbstractItem {
    /**тип нашего предмета**/
    protected  EquipTypes equipType;


    public enum EquipTypes{
        HELMET,
        SHOULDERS,
        BODY,
        ARMS,
        PANTS,
        FEET,
        RING,
        NECK;

        @Override
        public String toString() {
            String s = "";
            switch (this) {
                case HELMET: s = "Шлем";
                    break;
                case SHOULDERS: s = "Наплечники";
                    break;
                case BODY: s = "Доспех";
                    break;
                case ARMS: s = "Перчатки";
                    break;
                case PANTS: s = "Штаны";
                    break;
                case FEET: s = "Обувь";
                    break;
                case RING: s = "Перчатки";
                    break;
                case NECK: s = "Кольцо";
                    break;
            }
            return s;
        }
    }
}
