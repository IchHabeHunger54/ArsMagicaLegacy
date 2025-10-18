package at.minecraftschurli.arsmagicalegacy.api.etherium;

import net.minecraft.core.Holder;

/**
 * Represents an etherium capability handler.
 */
public interface EtheriumHandler {
    /**
     * @param type The {@link EtheriumType} to get the stored amount for.
     * @return The amount of the {@link EtheriumType} stored in the handler.
     */
    int getAmount(Holder<EtheriumType> type);

    /**
     * @param type The {@link EtheriumType} to get the maximum amount for.
     * @return The maximum amount of the {@link EtheriumType} that can be stored in the handler.
     */
    int getMaxAmount(Holder<EtheriumType> type);

    /**
     * Sets the stored amount for the given {@link EtheriumType}.
     *
     * @param type   The {@link EtheriumType} to set the stored amount for.
     * @param amount The stored amount to set.
     */
    void setAmount(Holder<EtheriumType> type, int amount);

    /**
     * Adds the given amount to the stored amount for the given {@link EtheriumType}.
     * <p>
     * Returns the amount left to be added. For example, if all etherium was successfully added,
     * 0 will be returned. If the handler is full, the amount will be returned.
     *
     * @param type   The {@link EtheriumType} to add the given amount for.
     * @param amount The amount to add.
     * @return The amount left to be added.
     */
    int addAmount(Holder<EtheriumType> type, int amount);

    /**
     * Subtracts the given amount to the stored amount for the given {@link EtheriumType}.
     * <p>
     * Returns the amount left to be subtracted. For example, if all etherium was successfully subtracted,
     * 0 will be returned. If the handler is full, the amount will be returned.
     *
     * @param type   The {@link EtheriumType} to subtract the given amount for.
     * @param amount The amount to subtract.
     * @return The amount left to be subtracted.
     */
    int subtractAmount(Holder<EtheriumType> type, int amount);
}
