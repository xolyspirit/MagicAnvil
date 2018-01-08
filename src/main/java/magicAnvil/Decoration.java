package magicAnvil;

public class Decoration {
    /**Название украшения**/
    private String name;
    /**для добавки к предмету**/
    private String addedToCraft;
    /**Эффект от украшения**/
    private Effect effect;
    /**материал использованный для украшения**/
    private Material material;

    @Override
    public String toString() {
        return name;
    }

    //стандартные геттеры сеттеры
    public String getAddedToCraft() {
        return addedToCraft;
    }

    public void setAddedToCraft(String addedToCraft) {
        this.addedToCraft = addedToCraft;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
