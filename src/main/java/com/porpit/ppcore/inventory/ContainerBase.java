package com.porpit.ppcore.inventory;

import com.porpit.ppcore.tileentity.TileEntityContainerBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public abstract class ContainerBase extends Container {

    protected IItemHandler AllSlot;
    protected TileEntityContainerBase tileEntity;
    protected EntityPlayer player;

    public ContainerBase(EntityPlayer player, TileEntityContainerBase tileEntity) {
        this.tileEntity = (TileEntityContainerBase) tileEntity;
        this.player = player;
        AllSlot = tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, player.getHorizontalFacing());
        addSlot();
    }
    protected abstract void addSlot();

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    public TileEntityContainerBase getTileEntity() {
        return tileEntity;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index < tileEntity.getRowCount() * tileEntity.getColumnCount())
            {
                if (!this.mergeItemStack(itemstack1, tileEntity.getRowCount() * tileEntity.getColumnCount(), this.inventorySlots.size(), true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, tileEntity.getRowCount() * tileEntity.getColumnCount(), false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }
}
