package atipera.atiperaintershiptask.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import atipera.atiperaintershiptask.dto.response.feign_client.GithubBranchResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse;
import atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse;
import atipera.atiperaintershiptask.exception.NotFoundException;
import atipera.atiperaintershiptask.feign_client.GithubClient;
import atipera.atiperaintershiptask.model.GithubModel;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GithubService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class GithubServiceTest {
        @MockBean
        private GithubClient githubClient;

        @Autowired
        private GithubService githubService;

        @Test
        void testGetAllReposByUsername() throws JsonProcessingException {
                // Arrange
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(new ArrayList<>());
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);

                // Act and Assert
                assertThrows(NotFoundException.class, () -> githubService.getAllReposByUsername("janedoe"));
                verify(githubClient).getAllRepositoriesByUsername(eq("janedoe"));
        }

        @Test
        void testGetAllReposByUsername2() throws JsonProcessingException {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(true);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);

                // Act
                List<GithubModel> actualAllReposByUsername = githubService.getAllReposByUsername("janedoe");

                // Assert
                verify(githubClient).getAllRepositoriesByUsername(eq("janedoe"));
                assertTrue(actualAllReposByUsername.isEmpty());
        }

        @Test
        void testGetAllReposByUsername3() throws JsonProcessingException {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(true);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                GithubOwnerResponse owner2 = new GithubOwnerResponse();
                owner2.setLogin("atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse");

                GithubRepositoryResponse githubRepositoryResponse2 = new GithubRepositoryResponse();
                githubRepositoryResponse2.setFork(false);
                githubRepositoryResponse2
                        .setName("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse");
                githubRepositoryResponse2.setOwner(owner2);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse2);
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                ArrayList<GithubBranchResponse> githubBranchResponseList = new ArrayList<>();
                Optional<List<GithubBranchResponse>> ofResult2 = Optional.of(githubBranchResponseList);
                when(githubClient.getAllBranchesByUsernameAndProjectName(Mockito.<String>any(), Mockito.<String>any()))
                        .thenReturn(ofResult2);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);

                // Act
                List<GithubModel> actualAllReposByUsername = githubService.getAllReposByUsername("janedoe");

                // Assert
                verify(githubClient).getAllBranchesByUsernameAndProjectName(eq("janedoe"),
                        eq("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse"));
                verify(githubClient).getAllRepositoriesByUsername(eq("janedoe"));
                assertEquals(1, actualAllReposByUsername.size());
                GithubModel getResult = actualAllReposByUsername.get(0);
                assertEquals("atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse",
                        getResult.getOwnerLogin());
                assertEquals("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse",
                        getResult.getRepositoryName());
                List<GithubBranchResponse> branches = getResult.getBranches();
                assertTrue(branches.isEmpty());
                assertSame(githubBranchResponseList, branches);
        }

        @Test
        void testGetAllReposByUsername4() throws JsonProcessingException {
                // Arrange
                GithubOwnerResponse owner = new GithubOwnerResponse();
                owner.setLogin("Login");

                GithubRepositoryResponse githubRepositoryResponse = new GithubRepositoryResponse();
                githubRepositoryResponse.setFork(true);
                githubRepositoryResponse.setName("Name");
                githubRepositoryResponse.setOwner(owner);

                GithubOwnerResponse owner2 = new GithubOwnerResponse();
                owner2.setLogin("atipera.atiperaintershiptask.dto.response.feign_client.GithubOwnerResponse");

                GithubRepositoryResponse githubRepositoryResponse2 = new GithubRepositoryResponse();
                githubRepositoryResponse2.setFork(false);
                githubRepositoryResponse2
                        .setName("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse");
                githubRepositoryResponse2.setOwner(owner2);

                ArrayList<GithubRepositoryResponse> githubRepositoryResponseList = new ArrayList<>();
                githubRepositoryResponseList.add(githubRepositoryResponse2);
                githubRepositoryResponseList.add(githubRepositoryResponse);
                Optional<List<GithubRepositoryResponse>> ofResult = Optional.of(githubRepositoryResponseList);
                Optional<List<GithubBranchResponse>> emptyResult = Optional.empty();
                when(githubClient.getAllBranchesByUsernameAndProjectName(Mockito.<String>any(), Mockito.<String>any()))
                        .thenReturn(emptyResult);
                when(githubClient.getAllRepositoriesByUsername(Mockito.<String>any())).thenReturn(ofResult);

                // Act and Assert
                assertThrows(NotFoundException.class, () -> githubService.getAllReposByUsername("janedoe"));
                verify(githubClient).getAllBranchesByUsernameAndProjectName(eq("janedoe"),
                        eq("atipera.atiperaintershiptask.dto.response.feign_client.GithubRepositoryResponse"));
                verify(githubClient).getAllRepositoriesByUsername(eq("janedoe"));
        }
}
