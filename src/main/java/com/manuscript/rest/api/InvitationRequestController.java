package com.manuscript.rest.api;

import com.manuscript.rest.request.InvitationRequestRequest;
import com.manuscript.rest.response.InvitationRequestResponse;
import com.manuscript.rest.service.IInvitationRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manuscript.rest.common.Constants.RESOURCE_INVITATION;

@RestController
@RequestMapping(RESOURCE_INVITATION)
@CrossOrigin()
public class InvitationRequestController {
    private final IInvitationRequestService requestService;

    public InvitationRequestController(IInvitationRequestService requestService) {
        this.requestService = requestService;
    }


    @GetMapping("/getAllInvitations")
    public List<InvitationRequestResponse> getAllInvitations() {
        return requestService.getAllInvitations();
    }

    @PostMapping("/createInvitation")
    public List<InvitationRequestResponse> createInvitation(@RequestBody InvitationRequestRequest invitationRequestRequest) {
        return requestService.save(invitationRequestRequest);
    }

    @GetMapping("/acceptRequest/{email}")
    public List<InvitationRequestResponse> acceptRequest(@PathVariable String email) {
        if (email.isEmpty()) {
            requestService.getAllInvitations();
        }
        return requestService.acceptRequest(email);
    }

    @GetMapping("/denyRequest/{email}")
    public List<InvitationRequestResponse> denyRequest(@PathVariable String email) {
        if (email.isEmpty()) {
            requestService.getAllInvitations();
        }
        return requestService.denyRequest(email);
    }


}
