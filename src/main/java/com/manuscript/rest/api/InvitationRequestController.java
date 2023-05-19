package com.manuscript.rest.api;

import com.manuscript.rest.request.InvitationRequestRequest;
import com.manuscript.rest.response.InvitationRequestResponse;
import com.manuscript.rest.service.IInvitationRequestService;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/createInvitation")
    public List<InvitationRequestResponse> createInvitation(@RequestBody InvitationRequestRequest invitationRequestRequest) {
        return requestService.save(invitationRequestRequest);
    }

    @GetMapping("/approveInvitationRequest/{email}")
    public List<InvitationRequestResponse> approveInvitationRequest(@PathVariable String email) {
        if (email.isEmpty()) {
            requestService.getAllInvitations();
        }
        return requestService.approveRequest(email);
    }

    @GetMapping("/denyInvitationRequest/{email}")
    public List<InvitationRequestResponse> denyInvitationRequest(@PathVariable String email) {
        if (email.isEmpty()) {
            requestService.getAllInvitations();
        }
        return requestService.denyRequest(email);
    }

    @GetMapping("/getAllInvitations")
    public ResponseEntity<List<InvitationRequestResponse>> getAllInvitations() {
        return ResponseEntity.ok(requestService.getAllInvitations());
    }
}
