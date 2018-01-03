package MagicAnvil;

public class Effect {
    /**Название эффекта**/
    private String effectName;
    /**Описание эффекта**/
    private String effectDescription;
    /**Шанс прока эффекта**/
    private Byte chance;
    /**Тип эффекта**/
    private EffectType effectType;
    /**стихия эффекта**/
    private Material.Elements element;

    public enum EffectType{
        ATTACK,
        DEFENSE,
        CRAFT,
        FURNITURE
    }

    public String getNameWithPercentages(Integer multiplier){
        StringBuilder s = new StringBuilder();
        Integer res = chance*multiplier;
        if(res!=0) {
            s.append(effectName + ":\n");
            s.append(effectDescription + "\n");
            s.append(" Шанс срабатывания " + res + " процентов \n ******** \n");
        }

        return s.toString();
    }

    public Material.Elements getElement() {
        return element;
    }

    public void setElement(Material.Elements element) {
        this.element = element;
    }

    public EffectType getEffectType() {
        return effectType;
    }

    public void setEffectType(EffectType effectType) {
        this.effectType = effectType;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public String getEffectDescription() {
        return effectDescription;
    }

    public void setEffectDescription(String effectDescription) {
        this.effectDescription = effectDescription;
    }

    public Byte getChance() {
        return chance;
    }

    public void setChance(Byte chance) {
        this.chance = chance;
    }
}
