package pl.lotto.numberreceiver;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

public class InMemoryNumberReceiverRepositoryTestImpl implements NumberReceiverRepository {

    Map<UUID, LotteryTicket> database = new ConcurrentHashMap<>();

    @Override
    public LotteryTicket save(LotteryTicket lotteryTicket) {
        return database.put(lotteryTicket.getLotteryId(), lotteryTicket);
    }

    @Override
    public List<LotteryTicket> findAllByDrawDate(LocalDateTime date) {
        return database.values()
                .stream()
                .filter(lotteryTicket -> lotteryTicket.getDrawDate().isEqual(date))
                .toList();
    }

    @Override
    public <S extends LotteryTicket> S insert(S entity) {
        throw new ImplementMeException();
    }

    @Override
    public <S extends LotteryTicket> List<S> insert(Iterable<S> entities) {
        throw new ImplementMeException();
    }

    @Override
    public <S extends LotteryTicket> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends LotteryTicket> List<S> findAll(Example<S> example) {
        throw new ImplementMeException();
    }

    @Override
    public <S extends LotteryTicket> List<S> findAll(Example<S> example, Sort sort) {
        throw new ImplementMeException();
    }

    @Override
    public <S extends LotteryTicket> Page<S> findAll(Example<S> example, Pageable pageable) {
        throw new ImplementMeException();
    }

    @Override
    public <S extends LotteryTicket> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends LotteryTicket> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends LotteryTicket, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        throw new ImplementMeException();
    }

    @Override
    public <S extends LotteryTicket> List<S> saveAll(Iterable<S> entities) {
        throw new ImplementMeException();
    }

    @Override
    public Optional<LotteryTicket> findById(UUID id) {
        return database.keySet()
                .stream()
                .filter(lotteryTicket -> lotteryTicket.equals(id))
                .map(database::get)
                .findFirst();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return false;
    }

    @Override
    public List<LotteryTicket> findAll() {
        throw new ImplementMeException();
    }

    @Override
    public List<LotteryTicket> findAllById(Iterable<UUID> uuids) {
        throw new ImplementMeException();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uuid) {

    }

    @Override
    public void delete(LotteryTicket entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uuids) {

    }

    @Override
    public void deleteAll(Iterable<? extends LotteryTicket> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<LotteryTicket> findAll(Sort sort) {
        throw new ImplementMeException();
    }

    @Override
    public Page<LotteryTicket> findAll(Pageable pageable) {
        throw new ImplementMeException();
    }
}
