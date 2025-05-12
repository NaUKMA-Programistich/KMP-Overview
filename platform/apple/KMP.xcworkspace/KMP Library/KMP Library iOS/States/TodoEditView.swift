import SwiftUI
import SharedDomain

struct TodoEditView: View {
    let todo: Todo
    let onUpdate: (Int32, String, Bool) -> Void
    let onDelete: (Int32) -> Void
    let onDismiss: () -> Void

    @State private var title: String
    @State private var completed: Bool

    init(todo: Todo, onUpdate: @escaping (Int32, String, Bool) -> Void, onDelete: @escaping (Int32) -> Void, onDismiss: @escaping () -> Void) {
        self.todo = todo
        self.onUpdate = onUpdate
        self.onDelete = onDelete
        self.onDismiss = onDismiss

        _title = State(initialValue: todo.title)
        _completed = State(initialValue: todo.completed)
    }

    var body: some View {
        TodoDialogView(title: "Edit Todo") {
            VStack(spacing: 16) {
                TextField("Title", text: $title)
                    .textFieldStyle(RoundedBorderTextFieldStyle())

                HStack {
                    Text("Completed")
                    Spacer()
                    Toggle("", isOn: $completed)
                        .labelsHidden()
                }

                HStack {
                    Button("Delete") {
                        onDelete(todo.id)
                        onDismiss()
                    }
                    .foregroundColor(.red)

                    Spacer()

                    Button("Cancel") {
                        onDismiss()
                    }

                    Button("Save") {
                        onUpdate(todo.id, title, completed)
                        onDismiss()
                    }
                    .disabled(title.isEmpty)
                    .buttonStyle(.borderedProminent)
                }
                .padding(.top, 8)

                Spacer()
            }
        }
    }
}
