package agencyBrandEntities;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.MassItem;
import translator.TranslateGlossary;
import util.CosmeticUtil;
import util.Formatter;
import util.GrobalDefined;

public class MassItemEcco extends BaseItemCosmetic {
    private static final Logger LOGGER = LogManager.getLogger(MassItemBirken.class);

    private int priceWon;
    private int priceSaleWon;
    private int priceSubstractWon;
    private MassItem massItem;
    
    public MassItemEcco(double priceEuro) {
        this.priceWon = super.calculatePriceCommisionVATWon(priceEuro, 0);
    }
    
    public MassItemEcco(MassItem massItem) {
        this.massItem = massItem;
        this.priceWon = super.calculatePriceCommisionSectionWon(massItem.getItemPriceEuro());
//        if(massItem.isItemSale()) {
//            this.priceSaleWon = super.calculatePriceCommisionVATWon(massItem.getItemSalePriceEuro(), 0);
//            this.priceSubstractWon = priceWon - priceSaleWon;
//        }
        if(GrobalDefined.TRANSLATE) {
            //invokeTranslateDescription(massItem);
            invokeTranslateItemTitleKor(massItem);
        }
    }
    
    private void invokeTranslateDescription(MassItem massItem) {
        String description = massItem.getItemDescription();
        if (description != null) {
            String translatedDescription = "";
            try {
                translatedDescription = TranslateGlossary.translateTextWithGlossary(description);
            } catch (FileNotFoundException e) {
                LOGGER.error("TranslatedDescription has error:" + description, e);
            } catch (IOException e) {
                LOGGER.error("TranslatedDescription has error:" + description, e);
            }
            massItem.setItemDescriptionKor(translatedDescription);
        } else {
            massItem.setItemDescriptionKor("");
            LOGGER.error("Description is not found! No transation:" + massItem.getItemTitleDE());
        }
    }
    
    private void invokeTranslateItemTitleKor(MassItem massItem) {
        String itemTitleDe = massItem.getItemTitleDE();
        if (itemTitleDe != null) {
            String translatedItemTiteKor = "";
            try {
                translatedItemTiteKor = TranslateGlossary.translateTextWithGlossary(itemTitleDe);
            } catch (FileNotFoundException e) {
                LOGGER.error("TranslatedDescription has error:" + itemTitleDe, e);
            } catch (IOException e) {
                LOGGER.error("TranslatedDescription has error:" + itemTitleDe, e);
            }
            massItem.setItemTitleKor(translatedItemTiteKor);
        } else {
            massItem.setItemTitleKor("");
            LOGGER.error("ItemTileDe is not found! No transation:" + massItem.getItemTitleDE());
        }
    }
    
    @Override
    public String getCategoryId() {
        return massItem.getCategoryId();
    }

    @Override
    public String getItemFullnameDE() {
        return massItem.getBrandNameDE() + " " + massItem.getItemTitleDE();
    }

    @Override
    public String getItemFullnameKor() {
        return massItem.getItemTitleKor() + " " + massItem.getItemVolume();
    }

    @Override
    public MassItem getMassItem() {
        return massItem;
    }

    @Override
    public String getPriceWonString() {
        return String.valueOf(priceWon);
    }

    @Override
    public String getPriceSaleWonString() {
        return null;
    }

    @Override
    public String getMainImageFileName() {
        return massItem.getMainImageFileName();
    }

    @Override
    public String getItemFullDescriptionKOR() {
        StringBuilder result = new StringBuilder();
        //result.append(getItemFullNameHtml(getItemFullnameKor()));
        result.append(getItemBrandOverview(CosmeticUtil.convertItemOverviewCosmetic(massItem.getBrandNameDE())));
        result.append(getEmptyLineHtml());
        result.append(getItemBrandNameHtml(massItem.getBrandNameDE()));
        result.append(getEmptyLineHtml());
        result.append(getItemDescriptionHtml(Formatter.setLinebreakAfterPunctHtml(massItem.getItemDescriptionKor())));
        result.append(getEmptyLineHtml());
        //result.append(getItemUsageHtml(Formatter.setLinebreakAfterPunctHtml(massItem.getItemUsage())));
        //https://www.ecco-verde.de/avril/organic-moisturizing-face-mask
        //result.append(getEmptyLineHtml());
        result.append(getItemIngredientHtml(massItem.getItemIngredients()));
        result.append(getEmptyLineHtml());
        result.append(getTranslateInfoHtml());
        return addTopBottomInfo(result.toString());
    }

    @Override
    public String getItemFullnameWithPrefix() {
        return "[" + massItem.getBrandNameDE() + " " + massItem.getItemCategory() + "] " + getItemFullnameKor();

    }

    @Override
    public String getItemFullDescriptionManual() {
        return null;
    }

    @Override
    public String getItemFullDescriptionDE() {
        StringBuilder result = new StringBuilder();
        //result.append(getItemFullNameHtml(getItemFullnameKor()));
        result.append(getItemBrandOverview(CosmeticUtil.convertItemOverviewCosmetic(massItem.getBrandNameDE())));
        result.append(getEmptyLineHtml());
        result.append(getItemBrandNameHtml(massItem.getBrandNameDE()));
        result.append(getEmptyLineHtml());
        result.append(getItemDescriptionHtml(Formatter.setLinebreakAfterPunctHtml(massItem.getItemDescription())));
        result.append(getEmptyLineHtml());
        //result.append(getItemUsageHtml(Formatter.setLinebreakAfterPunctHtml(massItem.getItemUsage())));
        //result.append(getEmptyLineHtml());
        result.append(getItemIngredientHtml(massItem.getItemIngredients()));
        result.append(getEmptyLineHtml());
        result.append(getTranslateInfoHtml());
        return addTopBottomInfo(result.toString());
    }
}
