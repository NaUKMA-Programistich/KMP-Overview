import SwiftUI
import SharedDomain

struct TodoListScreen: View {
    @StateObject private var viewModel = TodoViewModelWrapper()
    @State private var isCreatingTodo = false

    var body: some View {
        NavigationView {
            ZStack {
                TodoListContent(
                    state: viewModel.state,
                    onRefresh: viewModel.getAllTodo,
                    onUpdate: viewModel.updateTodo,
                    onDelete: viewModel.deleteTodo
                )
            }
            .navigationTitle("Todo List")
            .toolbar {
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: viewModel.getAllTodo) {
                        Image(systemName: "arrow.clockwise")
                    }
                    .disabled(viewModel.state.isLoading)
                }
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button(action: { isCreatingTodo = true }) {
                        Image(systemName: "plus")
                    }
                    .disabled(!viewModel.state.isSuccess)
                }
            }
            .sheet(isPresented: $isCreatingTodo) {
                TodoCreateView(
                    onCreate: { title, completed in
                        viewModel.createTodo(title: title, completed: completed)
                        isCreatingTodo = false
                    },
                    onDismiss: { isCreatingTodo = false }
                )
            }
        }
    }
}
