package banz.ai.marketing.bot.modelbehavior.behavior;

import banz.ai.marketing.bot.modelbehavior.behavior.entity.Dialog;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelRequest;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.ModelResponse;
import banz.ai.marketing.bot.modelbehavior.behavior.entity.*;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.DialogRepository;
import banz.ai.marketing.bot.modelbehavior.behavior.repository.ModelRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Objects;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
public class ModelBehaviorJPADomainRepository implements ModelBehaviorDomainRepository {

  private final DialogRepository dialogRepository;
  private final ModelRequestRepository requestRepository;

  @Override
  public void save(ModelBehaviorAggregate behaviorAggregate) {
    Dialog dialog;
    var root = behaviorAggregate.root;
    if (dialogRepository.existsById(root.dialogId)) {
      dialog = dialogRepository.getReferenceById(root.dialogId);
    } else {
      dialog = new Dialog();
      dialog.setId(root.dialogId);
      dialog.setCreatedAt(new Date());
    }
    var request = new ModelRequest();
    request.setDialog(dialog);
    request.setText(root.request.text);
    request.setOperator(root.request.isOperator());
    IntStream.range(0, root.request.messages.size())
            .mapToObj(i -> {
              var m = new ModelRequestMessage();
              m.setContent(root.request.messages.get(i).content());
              m.setOrdinalNumber(i);
              return m;
            }).forEach(request::addMessage);
    if (Objects.nonNull(root.response)) {
      var response = new ModelResponse();
      root.response.stopTopics().stream()
              .map(c -> {
                var stopTopic = new StopTopic();
                stopTopic.setContent(c);
                return stopTopic;
              })
              .forEach(response::addStopTopic);
      response.setDialogEvaluation(root.response.dialogEvaluation());
      response.setOfferPurchase(root.response.offerPurchase());
      request.setModelResponse(response);
    }
    dialogRepository.save(dialog);
    requestRepository.save(request);
  }

  @Override
  public ModelBehaviorAggregate getById(long id) {
    return null;
  }
}
