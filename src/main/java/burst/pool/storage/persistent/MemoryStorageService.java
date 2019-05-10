package burst.pool.storage.persistent;

import burst.kit.entity.BurstAddress;
import burst.kit.entity.BurstValue;
import burst.pool.entity.Payout;
import burst.pool.entity.WonBlock;
import burst.pool.miners.Deadline;
import burst.pool.miners.Miner;
import burst.pool.miners.MinerMaths;
import burst.pool.miners.PoolFeeRecipient;
import burst.pool.pool.StoredSubmission;
import burst.pool.pool.Submission;
import burst.pool.storage.config.PropertyService;
import burst.pool.storage.config.Props;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class MemoryStorageService implements StorageService {

    private final PropertyService propertyService;
    private final MinerMaths minerMaths;

    private final Map<BurstAddress, Miner> miners = new HashMap<>();
    private final Map<Long, StoredSubmission> bestSubmissionForBlock = new HashMap<>();
    private final AtomicReference<PoolFeeRecipient> poolFeeRecipient = new AtomicReference<>();
    private final AtomicInteger lastProcessedBlock = new AtomicInteger(0);

    public MemoryStorageService(PropertyService propertyService, MinerMaths minerMaths) {
        this.propertyService = propertyService;
        this.minerMaths = minerMaths;
        poolFeeRecipient.set(new PoolFeeRecipient(propertyService, new MemoryFeeRecipientStore()));
    }

    @Override
    public StorageService beginTransaction() {
        return this;
    }

    @Override
    public void commitTransaction() {
        // TODO Not supported
    }

    @Override
    public void rollbackTransaction() {
        // TODO Not supported
    }

    @Override
    public int getMinerCount() {
        synchronized (miners) {
            return miners.size();
        }
    }

    @Override
    public List<Miner> getMiners() {
        synchronized (miners) {
            return new ArrayList<>(miners.values());
        }
    }

    @Override
    public Miner getMiner(BurstAddress address) {
        synchronized (miners) {
            return miners.get(address);
        }
    }

    @Override
    public Miner newMiner(BurstAddress address) {
        Miner miner =  new Miner(minerMaths, propertyService, address, new MemoryMinerStore());
        miners.put(address, miner);
        return miner;
    }

    @Override
    public PoolFeeRecipient getPoolFeeRecipient() {
        synchronized (poolFeeRecipient) {
            return poolFeeRecipient.get();
        }
    }

    @Override
    public int getLastProcessedBlock() {
        synchronized (lastProcessedBlock) {
            return lastProcessedBlock.get();
        }
    }

    @Override
    public void incrementLastProcessedBlock() {
        synchronized (lastProcessedBlock) {
            lastProcessedBlock.updateAndGet(val -> val+1);
        }
    }

    @Override
    public Map<Long, StoredSubmission> getBestSubmissions() {
        synchronized (bestSubmissionForBlock) {
            return bestSubmissionForBlock;
        }
    }

    @Override
    public StoredSubmission getBestSubmissionForBlock(long blockHeight) {
        synchronized (bestSubmissionForBlock) {
            return bestSubmissionForBlock.get(blockHeight);
        }
    }

    @Override
    public void setOrUpdateBestSubmissionForBlock(long blockHeight, StoredSubmission submission) {
        synchronized (bestSubmissionForBlock) {
            bestSubmissionForBlock.put(blockHeight, submission);
        }
    }

    @Override
    public void removeBestSubmission(long blockHeight) {
        synchronized (bestSubmissionForBlock) {
            bestSubmissionForBlock.get(blockHeight);
        }
    }

    @Override
    public void addWonBlock(WonBlock wonBlock) {

    }

    @Override
    public void addPayout(Payout payout) {

    }

    @Override
    public void close() throws Exception {
        // Do nothing
    }

    private class MemoryMinerStore implements MinerStore {
        private volatile BurstValue pendingBalance = BurstValue.fromBurst(0);
        private volatile double estimatedCapacity;
        private volatile double share;
        private volatile BurstValue minimumPayout = BurstValue.fromBurst(propertyService.getFloat(Props.defaultMinimumPayout));
        private volatile String name;
        private volatile String userAgent;
        private final Map<Long, Deadline> deadlines = new ConcurrentHashMap<>();

        @Override
        public BurstValue getPendingBalance() {
            return pendingBalance;
        }

        @Override
        public void setPendingBalance(BurstValue pendingBalance) {
            this.pendingBalance = pendingBalance;
        }

        @Override
        public double getEstimatedCapacity() {
            return estimatedCapacity;
        }

        @Override
        public void setEstimatedCapacity(double estimatedCapacity) {
            this.estimatedCapacity = estimatedCapacity;
        }

        @Override
        public double getShare() {
            return share;
        }

        @Override
        public void setShare(double share) {
            this.share = share;
        }

        @Override
        public BurstValue getMinimumPayout() {
            return minimumPayout;
        }

        @Override
        public void setMinimumPayout(BurstValue minimumPayout) {
            this.minimumPayout = minimumPayout;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String getUserAgent() {
            return userAgent;
        }

        @Override
        public void setUserAgent(String userAgent) {
            this.userAgent = userAgent;
        }

        @Override
        public List<Deadline> getDeadlines() {
            return new ArrayList<>(deadlines.values());
        }

        @Override
        public int getDeadlineCount() {
            return deadlines.size();
        }

        @Override
        public void removeDeadline(long height) {
            deadlines.remove(height);
        }

        @Override
        public Deadline getDeadline(long height) {
            return deadlines.get(height);
        }

        @Override
        public void setOrUpdateDeadline(long height, Deadline deadline) {
            deadlines.put(height, deadline);
        }
    }

    private static class MemoryFeeRecipientStore implements MinerStore.FeeRecipientStore {
        private AtomicReference<BurstValue> pending = new AtomicReference<>(BurstValue.fromBurst(0));

        @Override
        public BurstValue getPendingBalance() {
            return pending.get();
        }

        @Override
        public void setPendingBalance(BurstValue pending) {
            this.pending.set(pending);
        }
    }
}
