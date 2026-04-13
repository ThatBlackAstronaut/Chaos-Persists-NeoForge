package com.astryxion.chaospersists.render;

import com.astryxion.chaospersists.entity.BerthaHit;
import com.astryxion.chaospersists.entity.SunspotUrchin;
import com.astryxion.chaospersists.item.WaterBall;
import com.astryxion.chaospersists.item.InkSack;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;

public class RenderItemUrchin extends RenderSpinner
{
  public RenderItemUrchin(RenderManager manager) {
    super(manager);
  }

  @Override
  public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
  {
    if ((par1Entity instanceof BerthaHit)) {
      return;
    }
    if ((par1Entity instanceof SunspotUrchin)) {
      SunspotUrchin var2 = (SunspotUrchin)par1Entity;
      this.spinItemIconIndex = var2.getUrchinIndex();
    }
    if ((par1Entity instanceof WaterBall)) {
      WaterBall var2 = (WaterBall)par1Entity;
      this.spinItemIconIndex = var2.getWaterBallIndex();
    }
    if ((par1Entity instanceof InkSack)) {
      InkSack var2 = (InkSack)par1Entity;
      this.spinItemIconIndex = var2.getInkSackIndex();
    }
    super.doRender(par1Entity, par2, par4, par6, par8, par9);
  }
}
