package MagicAnvil;

import java.util.ArrayList;
import java.util.HashMap;

public class Material {
    /**Имя мятерала, которое мы будем использовать снаружи**/
    private String materialName;
    /**сделано из...**/
    private String craftedOfName;
    /** с добавлениями**/
    private String addedToCraftName;
    /** для каждого экземпляра материала этот показатель может быть разным**/
    private Quality quality;
    /** шкура дракона явно крепче шкурки кота. Мультипликатор показывает насколько**/
    private double qualityMultiplicator;
    /** стихийные свойства материала - единые для всех экземпляров материала**/
    private HashMap<Elements, Integer> elements;
    /** список эффектов, которые дает материал**/
    private ArrayList<Effect> effects;
    /** указывает на атрибут которые добавляет данный материал**/
    private Stats stat;
    /** указывает на преобладающий цвет материала**/
    private Colors color;
    /** тип материала**/
    private MaterialType materialType;

    /**список возможных цветов.**/
    public enum Colors {
        AIR_YELLOW,
        FIRE_ORANGE,
        EARTH_BROWN,
        WATER_BLUE,
        LIVE_GREEN,
        DEATH_PURPLE,
        LIGHT_WHITE,
        DARK_BLACK,
        CHAOS_SHIFTING,
        ORDER_GRAY,
        BLOOD_RED,
        SHADOW_SMOKY_GRAY;

        @Override
        public String toString() {
            String s = "";
            switch (this) {
                case AIR_YELLOW: s = "желтый";
                    break;
                case FIRE_ORANGE: s = "оранжевый";
                    break;
                case EARTH_BROWN: s = "коричневый";
                    break;
                case WATER_BLUE: s = "синий";
                    break;
                case LIVE_GREEN: s = "зеленый";
                    break;
                case DEATH_PURPLE: s = "фиолетовый";
                    break;
                case LIGHT_WHITE: s = "белый";
                    break;
                case DARK_BLACK: s = "черный";
                    break;
                case CHAOS_SHIFTING: s = "меняющийся";
                    break;
                case ORDER_GRAY: s = "серый";
                    break;
                case BLOOD_RED: s = "красный";
                    break;
                case SHADOW_SMOKY_GRAY: s = "темно-серый";
                    break;
            }
            return s;
        }
        public String addedName(){
            String s = "";
            switch (this) {
                case AIR_YELLOW: s = "желтого";
                    break;
                case FIRE_ORANGE: s = "оранжевого";
                    break;
                case EARTH_BROWN: s = "коричневого";
                    break;
                case WATER_BLUE: s = "синего";
                    break;
                case LIVE_GREEN: s = "зеленого";
                    break;
                case DEATH_PURPLE: s = "фиолетового";
                    break;
                case LIGHT_WHITE: s = "белого";
                    break;
                case DARK_BLACK: s = "черного";
                    break;
                case CHAOS_SHIFTING: s = "меняющигося";
                    break;
                case ORDER_GRAY: s = "серого";
                    break;
                case BLOOD_RED: s = "красного";
                    break;
                case SHADOW_SMOKY_GRAY: s = "темно-серого";
                    break;
            }
            return s;
        }
    }

    /**список возможных уровней качества.**/
    public enum Quality {
        BAD,
        COMMON,
        GOOD,
        IDEAL;

        @Override
        public String toString() {
            String s = "";
            switch (this) {

                case BAD: s = "плохое";
                    break;
                case COMMON: s = "обычное";
                    break;
                case GOOD: s = "редкое";
                    break;
                case IDEAL: s = "идеальное!";
                    break;
            }
            return s;
        }

        public Byte getQualityDivider(){
            Byte b= 0;
            switch (this){
                case BAD: b = 10;
                break;
                case COMMON: b = 5;
                break;
                case GOOD: b = 2;
                break;
                case IDEAL: b = 1;
            }
            return b;
        }

    }

    /**список возможных статов.**/
    public enum Stats {
        ARMOR,
        AGILITY,
        ENDURANCE,
        STRENGTH,
        CONSTITUTION,
        INTELLIGENCE,
        WISDOM,
        LUCK,
        PHISIC_CRIT_RATE,
        MAGIC_CRIT_RATE,
        PHISIC_CRIT_CHANCE,
        MAGIC_CRIT_CHANCE,
        ACCURACY,
        PENETRATION;

        public String addedStat(){
            String s = "";
            switch (this) {
                case ARMOR: s = " к броне";
                    break;
                case AGILITY: s = " к ловкости";
                    break;
                case ENDURANCE: s = " к выносливости";
                    break;
                case STRENGTH: s = " к силы";
                    break;
                case CONSTITUTION: s = " к телосложения";
                    break;
                case INTELLIGENCE: s = " к интеллекта";
                    break;
                case WISDOM: s = " к мудрости";
                    break;
                case LUCK: s = " к удачи";
                    break;
                case PHISIC_CRIT_RATE: s = " к критическому физ. урону";
                    break;
                case MAGIC_CRIT_RATE: s = " к критическому маг. урону";
                    break;
                case PHISIC_CRIT_CHANCE: s = " к шансу физ. крита";
                    break;
                case MAGIC_CRIT_CHANCE: s = " к шансу маг. крита";
                    break;
                case ACCURACY: s = " к точности";
                    break;
                case PENETRATION: s = " к проникновению магии";
                    break;
            }
            return s;
        }
    }

    /**список возможных стихий.**/
    public enum Elements {
        BASE,
        AIR,
        FIRE,
        EARTH,
        WATER,
        LIVE,
        DEATH,
        LIGHT,
        DARK,
        CHAOS,
        ORDER,
        BLOOD,
        SHADOW;

        public Stats getAssociatedStat(){
            Stats st = null;
            switch (this) {
                case BASE: st = Stats.CONSTITUTION;
                    break;
                case AIR: st = Stats.AGILITY;
                    break;
                case FIRE: st = Stats.INTELLIGENCE;
                    break;
                case EARTH: st = Stats.STRENGTH;
                    break;
                case WATER: st = Stats.WISDOM;
                    break;
                case LIVE: st = Stats.ENDURANCE;
                    break;
                case DEATH: st = Stats.PENETRATION;
                    break;
                case LIGHT: st = Stats.MAGIC_CRIT_CHANCE;
                    break;
                case DARK: st = Stats.MAGIC_CRIT_RATE;
                    break;
                case CHAOS: st = Stats.LUCK;
                    break;
                case ORDER: st = Stats.ACCURACY;
                    break;
                case BLOOD: st = Stats.PHISIC_CRIT_RATE;
                    break;
                case SHADOW: st = Stats.PHISIC_CRIT_CHANCE;
                    break;
            }
            return st;
        }
    }

    /**список возможных типов материала**/
    public enum MaterialType{
        METAL,
        WOOD,
        CLOTH,
        LIQUID,
        GAS,
        STONE,
        LEATHER,
        FIBER,
        LOOSE
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public void setMaterialType(MaterialType materialType) {
        this.materialType = materialType;
    }

    public String getCraftedOfName() {
        return craftedOfName;
    }

    public void setCraftedOfName(String craftedOfName) {
        this.craftedOfName = craftedOfName;
    }

    public String getAddedToCraftName() {
        return addedToCraftName;
    }

    public void setAddedToCraftName(String addedToCraftName) {
        this.addedToCraftName = addedToCraftName;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public double getQualityMultiplicator() {
        return qualityMultiplicator;
    }

    public void setQualityMultiplicator(double qualityMultiplicator) {
        this.qualityMultiplicator = qualityMultiplicator;
    }

    public HashMap<Elements, Integer> getElements() {
        return elements;
    }

    public void setElements(HashMap<Elements, Integer> elements) {
        this.elements = elements;
    }

    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void setEffects(ArrayList<Effect> effects) {
        this.effects = effects;
    }

    public Stats getStat() {
        return stat;
    }

    public void setStat(Stats stat) {
        this.stat = stat;
    }

    public Colors getColor() {
        return color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return getMaterialName();
    }
}





