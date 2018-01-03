package MagicAnvil;

import java.util.HashMap;
import java.util.HashSet;

public class Equipment extends AbstractItem {

    /**тип нашего предмета**/
    private  EquipTypes equipType;
    /**список статов предмета**/
    private HashMap<Material.Stats,Integer> stats;
    /**список работающих эффектов**/
    private HashSet<Effect> activeEffect;
    /**создание сразу предмета снаряжения соответствующего типа**/
    public Equipment(EquipTypes type){
        this.itemTypes = ItemTypes.EQUIPMENT;
        this.equipType= type;
    }
    /**конструктор по умолчанию**/
    public Equipment(){
        this.itemTypes = ItemTypes.EQUIPMENT;
    }
    /**Перечисляем все возможные види снаряжения (не считая оружия и бижутерии)**/
    public enum EquipTypes{
        HELMET,
        SHOULDERS,
        BODY,
        ARMS,
        PANTS,
        FEET;

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
            }
            return s;
        }
    }

    public EquipTypes getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipTypes equipType) {
        this.equipType = equipType;
    }

    //стандартные геттеры сеттеры

    public HashMap<Material.Stats, Integer> getStats() {
        return stats;
    }

    public void setStats(HashMap<Material.Stats,Integer> stats) {
        this.stats = stats;
    }

    public HashSet<Effect> getActiveEffect() {
        return activeEffect;
    }

    public void setActiveEffect(HashSet<Effect> activeEffect) {
        this.activeEffect = activeEffect;
    }
}
